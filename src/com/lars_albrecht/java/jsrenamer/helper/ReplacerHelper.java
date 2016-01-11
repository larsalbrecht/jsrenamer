package com.lars_albrecht.java.jsrenamer.helper;

import java.io.File;

public class ReplacerHelper {

	/**
	 * Returns the filepath of a file.
	 *
	 * @param file
	 * 		file to get filepath
	 *
	 * @return filePath
	 */
	public static String getFilepath(final File file) {
		return file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
	}
}
