package org.jboss.pvt.harness.utils;

import org.jboss.pvt.harness.exception.PVTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by rnc on 29/07/16.
 */
public class ClassVersionInspector
{
    private static Logger logger = LoggerFactory.getLogger( ClassVersionInspector.class );

    /**
     * Takes a Class filename and returns the ClassVersion.
     * @param filename valid class filename to check
     * @return ClassVersion of class.
     */
    public static ClassVersion checkClassVersion (String filename) throws PVTException
    {
        FileInputStream in;
        try
        {
            in = new FileInputStream( filename );
        }
        catch ( FileNotFoundException e )
        {
            throw new PVTException( "Classfile " + filename + " not found", e );
        }

        return checkClassVersion( in );
    }

    private static ClassVersion checkClassVersion (InputStream filename) throws PVTException
    {
        DataInputStream in = new DataInputStream( filename );

        int magic;
        try
        {
            magic = in.readInt();
        }
        catch ( IOException e )
        {
            throw new PVTException( "Invalid classfile for " + filename );
        }
        if ( magic != 0xcafebabe )
        {
            throw new PVTException( "Invalid classfile for " + filename );
        }
        try
        {
            int minor = in.readUnsignedShort();
            int major = in.readUnsignedShort();

            logger.debug ("Found class major {} and minor {} ", major, minor);

            return ClassVersion.parseInt( ( major + minor ) );
        }
        catch ( IOException e )
        {
            throw new PVTException( "Invalid classfile for " + filename, e );
        }
    }

    public static boolean checkJarVersion (String filename, ClassVersion version) throws PVTException
    {
        JarFile jar;
        try
        {
            jar = new JarFile( filename );

            Enumeration<JarEntry> entries = jar.entries();

            while ( entries.hasMoreElements() )
            {
                JarEntry entry = entries.nextElement();
                if ( entry.getName().endsWith( ".class" ) )
                {
                    ClassVersion cv = checkClassVersion( jar.getInputStream( entry ) );
                    if ( version != cv )
                    {
                        return false;
                    }
                }
            }
        }
        catch ( IOException e )
        {
            throw new PVTException( "Error processing jar file.", e );
        }

        return true;
    }

    public static boolean checkJarVersion (String filename, ClassVersion minVersion, ClassVersion maxVersion) throws PVTException
    {
        JarFile jar;
        try
        {
            jar = new JarFile( filename );

            Enumeration<JarEntry> entries = jar.entries();

            while ( entries.hasMoreElements() )
            {
                JarEntry entry = entries.nextElement();
                if ( entry.getName().endsWith( ".class" ) )
                {
                    ClassVersion cv = checkClassVersion( jar.getInputStream( entry ) );
                    if (cv.intValue() < minVersion.intValue() || cv.intValue() > maxVersion.intValue() )
                    {
                        return false;
                    }
                }
            }
        }
        catch ( IOException e )
        {
            throw new PVTException( "Error processing jar file.", e );
        }

        return true;
    }

}
