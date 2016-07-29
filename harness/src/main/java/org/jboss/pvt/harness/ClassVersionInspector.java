package org.jboss.pvt.harness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by rnc on 29/07/16.
 */
public class ClassVersionInspector
{
    private static Logger logger = LoggerFactory.getLogger( ClassVersionInspector.class );

    public enum ClassVersion
    {
        JAVA_12(46),
        JAVA_13(47),
        JAVA_14(48),
        JAVA_15(49),
        JAVA_16(50),
        JAVA_17(51),
        JAVA_18(52);

        private static Map<Integer, ClassVersion> map = new HashMap<>();
        static
        {
            for ( ClassVersion cv : ClassVersion.values() )
            {
                map.put( cv.byteValue, cv );
            }
        }

        private int byteValue;

        ClassVersion( int value )
        {
            this.byteValue = value;
        }

        public static ClassVersion parseInt (int value)
        {
            ClassVersion result = map.get( value );
            if ( result == null )
            {
                throw new PVTException( "Class version type not found for " + value );
            }
            return result;
        }
    }


    public static ClassVersion checkClassVersion (String filename)
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

    private static ClassVersion checkClassVersion (InputStream filename)
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

    public static boolean checkJarVersion (String filename, ClassVersion version)
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
}
