package org.jboss.pvt.harness;

import org.apache.commons.io.FileUtils;
import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.utils.ClassVersion;
import org.jboss.pvt.harness.utils.ClassVersionInspector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * Created by rnc on 29/07/16.
 */
public class ClassVersionInspectorTest
{
    @Rule
    public TemporaryFolder tf = new TemporaryFolder(  );


    @Test
    public void checkClassVersionTest() throws PVTException
    {
        String classFileName = ClassVersionInspector.class.getCanonicalName().replace( ".", "/" ) + ".class";
        File classFile = new File( ClassVersionInspector.class.getClassLoader().getResource( classFileName).getFile() );

        assertTrue( classFile.exists() );
        assertTrue( ClassVersionInspector.checkClassVersion( classFile.toString() ).equals(
                        ClassVersion.JAVA_18 ) );
    }

    @Test
    public void checkJarVersionTest() throws IOException, PVTException
    {
        File target = tf.newFile();
        FileUtils.copyURLToFile( new URL( "http://central.maven.org/maven2/commons-test/commons/test-0.1/commons-test-0.1.jar"), target);

        assertTrue( ClassVersionInspector.checkJarVersion( target.toString(), ClassVersion.JAVA_14 ) );
    }
}