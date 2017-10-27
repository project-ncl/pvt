/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.pvt.harness.utils;

import org.jboss.pvt.harness.exception.PVTSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by rnc on 02/10/16.
 */
public class ResourceUtils
{
    private static Logger logger = LoggerFactory.getLogger( ResourceUtils.class );

    /**
     *
     * @param zip local zip or http url
     * @return explored dir
     */
    public static File downloadZip(String zip )
    {
        File result = null;
        try
        {
            if ( isNotEmpty( zip ) )
            {
                // URL Based form
                if ( zip.contains( "://" ) )
                {
                    result = new File("target/" + zip.substring( zip.lastIndexOf( "/" ) + 1 ) );
                    if ( result.exists())
                    {
                        logger.warn( "Avoiding duplicate download of {} ", zip );
                    }
                    else
                    {
                        logger.info( "Copying URL {} to {}", zip, result );
                        org.apache.commons.io.FileUtils.copyURLToFile( new URL( zip ), result );
                    }
                }
                // Local file path
                else
                {

                    result = new File( System.getProperty( "basedir" ) + "/target/" + zip.substring( zip.lastIndexOf( "/" ) + 1 ) );
                    if ( result.exists() )
                    {
                        logger.warn( "Avoiding duplicate download of {} ", zip );
                    }
                    else
                    {
                        org.apache.commons.io.FileUtils.copyFile( new File( zip ), result );
                    }
                }
                logger.debug( "Create distribution {} ", result );
            }
        }
        catch ( IOException | ZipException e )
        {
            logger.error( "Caught ", e );
            throw new PVTSystemException( "Caught exception processing downloads: ", e );
        }
        return result;
    }


    /**
     *
     * @param zip local zip or http url
     * @return explored dir
     */
    public static File downloadZipExplored(String zip )
    {
        File result = null;
        try
        {
            if ( isNotEmpty( zip ) )
            {
                // URL Based form
                if ( zip.contains( "://" ) )
                {
                    result = new File("target/" + zip.substring( zip.lastIndexOf( "/" ) + 1 ) + ".unzipped" );
                    if ( result.exists())
                    {
                        logger.warn( "Avoiding duplicate download of {} ", zip );
                    }
                    else
                    {
                        logger.info( "Copying URL {} to {}", zip, result );
                        org.apache.commons.io.FileUtils.copyURLToFile( new URL( zip ), result );
                    }
                }
                // Local file path
                else
                {

                    result = new File( System.getProperty( "basedir" ) + "/target/" + zip.substring( zip.lastIndexOf( "/" ) + 1 ) + ".unzipped" );
                    if ( result.exists() )
                    {
                        logger.warn( "Avoiding duplicate download of {} ", zip );
                    }
                    else
                    {
                        org.apache.commons.io.FileUtils.copyFile( new File( zip ), result );
                    }
                }

                Collection<File> zipFiles;
                if (result.isFile()) {
                    zipFiles = new HashSet<>();
                    zipFiles.add(result);
                } else {
                    zipFiles = DirUtils.listFilesRecursively(result,  pathname -> pathname.isFile() && pathname.getName().endsWith(".zip"));
                }
                while(!zipFiles.isEmpty()) {
                    // Unzip them all
                    for (File zipFile : zipFiles) {
                        ZipUtil.explode(zipFile);
                    }
                    // Update zip files list and set for next recursion
                    zipFiles = DirUtils.listFilesRecursively(result, pathname -> pathname.isFile() && pathname.getName().endsWith(".zip"));
                }

                logger.debug( "Create distribution {} ", result );
            }
        }
        catch ( IOException | ZipException e )
        {
            logger.error( "Caught ", e );
            throw new PVTSystemException( "Caught exception processing downloads: ", e );
        }
        return result;
    }


}
