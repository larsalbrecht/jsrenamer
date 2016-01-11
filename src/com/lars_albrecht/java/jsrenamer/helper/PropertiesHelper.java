/**
 *
 */
package com.lars_albrecht.java.jsrenamer.helper;

import java.io.*;
import java.util.Properties;

/**
 * @author lalbrecht
 * @see "http://www.drdobbs.com/jvm/readwrite-properties-files-in-java/231000005"
 */
public class PropertiesHelper {

	private static final Properties properties = new Properties();
	private static final File       file       = new File("settings.properties");

	public PropertiesHelper() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				PropertiesHelper.saveProperties();
			}
		});
	}

	public static Properties getProperties() {
		return PropertiesHelper.properties;
	}

	public static void loadProperties() {
		InputStream is;

		// First try loading from the current directory
		try {
			is = new FileInputStream(PropertiesHelper.file);
		} catch (final Exception e) {
			is = null;
		}

		try {
			if (is == null) {
				// Try loading from classpath
				is = PropertiesHelper.class.getClass().getResourceAsStream(PropertiesHelper.file.getName());
			}

			// Try loading properties from the file (if found)
			PropertiesHelper.properties.load(is);
		} catch (final Exception e) {
			// do nothing
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void saveProperties() {
		OutputStream out = null;
		try {
			out = new FileOutputStream(PropertiesHelper.file);
			PropertiesHelper.properties.store(out, "Do not modify");
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
