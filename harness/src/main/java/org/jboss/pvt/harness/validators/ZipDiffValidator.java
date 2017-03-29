package org.jboss.pvt.harness.validators;

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.utils.ResourceUtils;
import org.jboss.pvt.harness.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 * Abstract Validator which diff against content in the two distributed zips
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ZipDiffValidator extends AbstractValidator<DiffValidation> {
    protected final Logger logger = LoggerFactory.getLogger( getClass() );

    public static final String PARAM_EXPECT_ADDS = "expectAdds";
    public static final String PARAM_EXPECT_REMOVES = "expectRemoves";
    public static final String PARAM_EXPECT_CHANGES = "expectChanges";
    public static final String PARAM_EXPECT_UNCHANGES = "expectUnchanges";

    public static final String PARAM_DIFF_VERSION = "diffVersion";

    public static final String PARAM_EXPECT_ADDS_COUNT = "expectAddCount";
    public static final String PARAM_EXPECT_REMOVE_COUNT = "expectRemoveCount";
    public static final String PARAM_EXPECT_CHANGE_COUNT = "expectChangeCount";
    public static final String PARAM_EXPECT_UNCHANGE_COUNT = "expectUnchangeCount";

    @Override
    public DiffValidation validate(List<String> resources, List<String> filters, Map<String, String> params) throws Exception {
        long startTime = System.currentTimeMillis();
        if(resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException("resources");
        }
        String[] expectAdds = {};
        if(params.containsKey(PARAM_EXPECT_ADDS)){
            expectAdds = params.get(PARAM_EXPECT_ADDS).split(",");
        }

        String[] expectRemoves = {};
        if(params.containsKey(PARAM_EXPECT_REMOVES)){
            expectRemoves = params.get(PARAM_EXPECT_REMOVES).split(",");
        }

        String[] expectChanges = {};
        if(params.containsKey(PARAM_EXPECT_CHANGES)){
            expectChanges = params.get(PARAM_EXPECT_CHANGES).split(",");
        }

        String[] expectUnchanges = {};
        if(params.containsKey(PARAM_EXPECT_UNCHANGES)){
            expectUnchanges = params.get(PARAM_EXPECT_UNCHANGES).split(",");
        }

        int expectAddCountMin = -1;
        int expectAddCountMax = -1;
        int expectRemoveCountMin = -1;
        int expectRemoveCountMax = -1;
        int expectChangeCountMin = -1;
        int expectChangeCountMax = -1;
        int expectUnchangeCountMin = -1;
        int expectUnchangeCountMax = -1;
        if(params.containsKey(PARAM_EXPECT_ADDS_COUNT)){
            String[] count = params.get(PARAM_EXPECT_ADDS_COUNT).trim().split(",");
            expectAddCountMin = Integer.parseInt(count[0].trim());
            expectAddCountMax = Integer.parseInt(count[1].trim());
        }
        if(params.containsKey(PARAM_EXPECT_REMOVE_COUNT)){
            String[] count = params.get(PARAM_EXPECT_REMOVE_COUNT).trim().split(",");
            expectRemoveCountMin = Integer.parseInt(count[0].trim());
            expectRemoveCountMax = Integer.parseInt(count[1].trim());

        }
        if(params.containsKey(PARAM_EXPECT_CHANGE_COUNT)){
            String[] count = params.get(PARAM_EXPECT_CHANGE_COUNT).trim().split(",");
            expectChangeCountMin = Integer.parseInt(count[0].trim());
            expectChangeCountMax = Integer.parseInt(count[1].trim());
        }
        if(params.containsKey(PARAM_EXPECT_UNCHANGE_COUNT)){
            String[] count = params.get(PARAM_EXPECT_UNCHANGE_COUNT).trim().split(",");
            expectUnchangeCountMin = Integer.parseInt(count[0].trim());
            expectUnchangeCountMax = Integer.parseInt(count[1].trim());
        }


        final List<File> filtered = new ArrayList<>();
        final List<File> added = new ArrayList<>();
        final List<File> removed = new ArrayList<>();
        final Map<String, File[]> changed = new java.util.HashMap<>();
        final List<File> unchanged = new ArrayList<>();


        String[] leftLinks = resources.get(0).split(",");
        String[] rightLinks = resources.get(1).split(",");
        if(leftLinks.length != rightLinks.length){
            throw new IllegalArgumentException("left resources size is not equal to right resources size.");
        }

        List<File> leftFiles = new ArrayList<>();
        List<File> rightFiles = new ArrayList<>();
        Map<String, File> leftFilesMap = new HashMap<>();
        Map<String, File> rightFilesMap = new HashMap<>();

        File[] leftDirs = new File[leftLinks.length];
        for(int i=0; i<leftLinks.length; i++){
            leftDirs[i] = ResourceUtils.downloadZipExplored(leftLinks[i]);
            Collection<File> files = FileUtils.listFiles(leftDirs[i], null, true);
            leftFiles.addAll(files);
            leftFilesMap.putAll(collectionToMap(leftDirs[i], files));
        }

        File[] rightDirs = new File[rightLinks.length];
        for(int i=0; i<leftLinks.length; i++){
            rightDirs[i] = ResourceUtils.downloadZipExplored(rightLinks[i]);
            Collection<File> files = FileUtils.listFiles(rightDirs[i], null, true);
            rightFiles.addAll(files);
            rightFilesMap.putAll(collectionToMap(rightDirs[i], files));
        }

        // Filter out left
        for (Iterator<Map.Entry<String, File>> it = leftFilesMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, File> entry = it.next();
            if(ValidatorUtils.filter(entry.getValue(), filters)){
                filtered.add(entry.getValue());
                it.remove();
            }
        }
        // Filter out right
        for (Iterator<Map.Entry<String, File>> it = rightFilesMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, File> entry = it.next();
            if(ValidatorUtils.filter(entry.getValue(), filters)){
                filtered.add(entry.getValue());
                it.remove();
            }
        }
        // Compare files
        Set<String> allFilenames = new HashSet<>();
        allFilenames.addAll(leftFilesMap.keySet());
        allFilenames.addAll(rightFilesMap.keySet());
        for (String name : allFilenames) {
            if (leftFilesMap.containsKey(name) && (!rightFilesMap.containsKey(name))) {
                removed.add(leftFilesMap.get(name));
            }
            else if (rightFilesMap.containsKey(name) && (!leftFilesMap.containsKey(name))) {
                added.add(rightFilesMap.get(name));
            }
            else if (leftFilesMap.containsKey(name) && (rightFilesMap.containsKey(name))) {
                File leftFile = leftFilesMap.get(name);
                File rightFile = rightFilesMap.get(name);
                if((leftFile.isDirectory() && rightFile.isDirectory())
                        || ( leftFile.isFile() && rightFile.isFile() && (leftFile.length() == rightFile.length()))) {
                    unchanged.add(rightFile);
                }
                else {
                    changed.put(name, new File[]{leftFile, rightFile});
                }
            }
        }

        List<File> allFiles = new ArrayList<>();
        allFiles.addAll(leftFiles);
        allFiles.addAll(rightFiles);

        List<File> allChangedFiles = new ArrayList<>();
        for(File[] files : changed.values()){
            allChangedFiles.add(files[0]);
            allChangedFiles.add(files[1]);
        }

        List<File> fails = new ArrayList<>();

        DiffValidation diffValidation = new DiffValidation(
                        meetExpects(allFiles, expectAdds, added, fails)
                        && meetExpects(allFiles, expectRemoves, removed, fails)
                        && meetExpects(allFiles, expectUnchanges, unchanged, fails)
                        && meetExpects(allFiles, expectChanges, allChangedFiles, fails), filtered, added, removed, unchanged, changed);
        diffValidation.setDuring(System.currentTimeMillis() - startTime);
        diffValidation.setFailed(fails);
        if(!meetExpectCount(expectAddCountMin, expectAddCountMax, added.size())
                || !meetExpectCount(expectRemoveCountMin, expectRemoveCountMax, removed.size())
                || !meetExpectCount(expectChangeCountMin, expectChangeCountMax, changed.size())
                || !meetExpectCount(expectUnchangeCountMin, expectUnchangeCountMax, unchanged.size())){
            diffValidation.setValid(false);
        }
        return diffValidation;
    }

    private boolean meetExpectCount(int min, int max, int length){
        if(length >= min && (max <= -1 || (max > -1 && length <= max))) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean meetExpects(List<File> allFiles, String[] expects, List<File> done, List<File> fails){
        if(expects == null || expects.length == 0) {
            return true;
        }
        for(File file : allFiles){
            for(String expect : expects){
                expect = expect.trim();
                if(expect.isEmpty()) {
                    continue;
                }
                if(file.getAbsolutePath().matches(expect) && !done.contains(file)) {
                    fails.add(file);
                }
            }
        }
        return fails.isEmpty();
    }

    private Map<String, File> collectionToMap(File dir, Collection<File> files){
        Map<String, File> map = new TreeMap<>();
        for(File file: files){ //TODO: if two files have same name
            String relativePath = file.getPath().substring(dir.getPath().length());
            relativePath = relativePath.substring(relativePath.indexOf("/", 2) + 1); // remove the root dir, such as jboss-6.0.0.Final
            if(map.containsKey(relativePath)){
                logger.warn("File name exists, " + relativePath);
            }
            map.put(relativePath, file);
        }
        return map;
    }

}
