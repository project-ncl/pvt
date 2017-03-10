package org.jboss.pvt.harness.validators;

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Abstract Validator which diff against content in the two distributed zips
 *
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class ZipDiffValidator implements Validator<DiffValidation> {
    protected final Logger logger = LoggerFactory.getLogger( getClass() );

    public static final String PARAM_EXPECT_ADDS = "expectAdds";
    public static final String PARAM_EXPECT_REMOVES = "expectRemoves";
    public static final String PARAM_EXPECT_CHANGES = "expectChanges";
    public static final String PARAM_EXPECT_UNCHANGES = "expectUnchanges";

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

        final List<File> filtered = new ArrayList<>();
        final List<File> added = new ArrayList<>();
        final List<File> removed = new ArrayList<>();
        final Map<String, File[]> changed = new java.util.HashMap<>();
        final List<File> unchanged = new ArrayList<>();

        File leftDir = ResourceUtil.downloadZipExplored(resources.get(0));
        File rightDir = ResourceUtil.downloadZipExplored(resources.get(1));

        Collection<File> leftFiles = FileUtils.listFiles(leftDir, null, true);
        Collection<File> rightFiles = FileUtils.listFiles(rightDir, null, true);
        Map<String, File> leftFilesMap = collectionToMap(leftFiles);
        Map<String, File> rightFilesMap = collectionToMap(rightFiles);

        // Filter out left
        for (Iterator<Map.Entry<String, File>> it = rightFilesMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, File> entry = it.next();
            if(Validator.filter(entry.getValue(), filters)){
                filtered.add(entry.getValue());
                it.remove();
            }
        }
        // Filter out right
        for (Iterator<Map.Entry<String, File>> it = rightFilesMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, File> entry = it.next();
            if(Validator.filter(entry.getValue(), filters)){
                filtered.add(entry.getValue());
                it.remove();
            }
        }
        // Compare files
        for (String name : leftFilesMap.keySet()) {
            if (leftFilesMap.containsKey(name) && (!rightFilesMap.containsKey(name))) {
                removed.add(leftFilesMap.get(name));
            }
            else if (rightFilesMap.containsKey(name) && (!leftFilesMap.containsKey(name))) {
                added.add(rightFilesMap.get(name));
            }
            else if (leftFilesMap.containsKey(name) && (rightFilesMap.containsKey(name))) {
                File leftFile = leftFilesMap.get(name);
                File rightFile = rightFilesMap.get(name);
                if((leftFile.isDirectory() == rightFile.isDirectory())
                        || ( leftFile.isFile() && rightFile.isFile() && (leftFile.length() == rightFile.length()))) {
                    unchanged.add(leftFile);
                }
                else {
                    changed.put(name, new File[]{leftFile, rightFile});
                }
            }
        }

        DiffValidation diffValidation = new DiffValidation(added, removed, unchanged, changed);
        diffValidation.setDuring(System.currentTimeMillis() - startTime);
        diffValidation.setFiltered(filtered);
        diffValidation.setValid(
                meetExpect(added, expectAdds)
                        && meetExpect(removed, expectRemoves)
                        && meetExpect(unchanged, expectUnchanges)
                        && meetExpect(changed.keySet(), expectChanges));
        return diffValidation;
    }

    private boolean meetExpect(List<File> files, String[] expects){
        if(expects == null || expects.length == 0) {
            return true;
        }
        for(String expect : expects){
            boolean meet = false;
            if(expect.trim().isEmpty()) {
                continue;
            }
            for(File file : files){
                if(file.getAbsolutePath().matches(expect)) {
                    meet = true;
                    break;
                }
            }
            if(!meet) {
                return false;
            }
        }
        return true;
    }

    private boolean meetExpect(Collection<String> files, String[] expects){
        if(expects == null || expects.length == 0) {
            return true;
        }
        for(String expect : expects){
            boolean meet = false;
            if(expect.trim().isEmpty()) {
                continue;
            }
            for(String file : files) {
                if(file.matches(expect)) {
                    meet = true;
                    break;
                }
            }
            if(!meet) {
                return false;
            }
        }
        return true;
    }

    private Map<String, File> collectionToMap(Collection<File> files){
        Map<String, File> map = new TreeMap<>();
        for(File file: files){
            map.put(file.getAbsolutePath(), file);
        }
        return map;
    }

}
