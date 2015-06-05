/**
 *
 */
package com.lars_albrecht.java.jsrenamer.helper;

import java.io.File;

/**
 * @author lalbrecht
 *
 */
public class FileHelper {

    public static String getFileExtension(final File file){
        return FileHelper.getFileExtension(file.getName());
    }

    public static String getFileExtension(final String filename){
        String result = null;

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            result = filename.substring(i+1);
        }

        return result;
    }

}
