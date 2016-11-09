package org.jboss.pvt.harness.validators;

import org.jboss.pvt.harness.utils.TestConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rnc on 12/08/16.
 */
public class ProductJarsPresentInRepoTest
{
    @Rule
    public final ProvideSystemProperty provideSystemProperty  = new ProvideSystemProperty
                    ( "PVTCFG", this.getClass().getResource( "/pvt.yaml").getFile().toString());

    @Rule
    public TestName testName = new TestName();

    @Rule
    public TemporaryFolder tf = new TemporaryFolder(  );

    private ProductJarsPresentInRepoValidator pjp;

    private TestConfiguration tc;

    private File distroDir;
    private File mavenRepo;

    @Before
    public void setUp() throws Exception
    {
        distroDir = tf.newFolder();
        mavenRepo = tf.newFolder();

        File subFolder = new File (distroDir, UUID.randomUUID().toString());
        subFolder.mkdir();
        createArchive( new File( distroDir, UUID.randomUUID().toString() + ".jar" ) );
        createArchive( new File( subFolder, UUID.randomUUID().toString() + ".jar" ) );

        tc = new TestConfiguration();
        tc.updateConfiguration( distroDir, null, mavenRepo, null);
        pjp = new ProductJarsPresentInRepoValidator();
    }

    private void createArchive(File name) throws IOException
    {
        FileOutputStream stream = new FileOutputStream( name );
        JarOutputStream out = new JarOutputStream( stream, new Manifest() );
        out.close();
        stream.close();
    }


    @Test
    public void validateJarsNotInRepo() throws Exception
    {
        assertFalse( pjp.validate(tc) );
    }

    @Test
    public void validateJarsInRepo() throws Exception
    {
        Files.walk( distroDir.toPath() ).forEach( p ->
                                                  {
                                                      try
                                                      {
                                                          Files.copy( p,
                                                                      new File( mavenRepo, p.toFile().getName() ).toPath(),
                                                                      StandardCopyOption.REPLACE_EXISTING);
                                                      }
                                                      catch ( IOException e )
                                                      {
                                                          throw new RuntimeException( e );
                                                      }
                                                  });

        assertTrue( pjp.validate(tc) );
    }

}