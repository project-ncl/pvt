package org.jboss.pvt.harness.utils;

import org.jboss.pvt.harness.exception.PVTException;
import org.jboss.pvt.harness.exception.PVTSystemException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rnc on 30/07/16.
 */
public enum ClassVersion
{
    JAVA_0(0), JAVA_12( 46 ), JAVA_13( 47 ), JAVA_14( 48 ), JAVA_15( 49 ), JAVA_16( 50 ), JAVA_17( 51 ), JAVA_18( 52 );

    private static Map<Integer, ClassVersion> map = new HashMap<>();

    static
    {
        for ( ClassVersion cv : ClassVersion.values() )
        {
            map.put( cv.byteValue, cv );
        }
    }

    private int byteValue;

    ClassVersion(int value )
    {
        this.byteValue = value;
    }

    public int intValue(){
        return this.byteValue;
    }

    public static ClassVersion parseInt(int value ) throws PVTSystemException
    {
        ClassVersion result = map.get( value );
        if ( result == null )
        {
            throw new PVTSystemException( "Class version type not found for " + value );
        }
        return result;
    }
}
