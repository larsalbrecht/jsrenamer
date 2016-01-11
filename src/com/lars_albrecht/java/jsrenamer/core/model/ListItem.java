/**
 *
 */
package com.lars_albrecht.java.jsrenamer.core.model;

import java.io.File;

/**
 * @author lalbrecht
 */
public class ListItem implements Cloneable {

	private String title = null;
	private File   file  = null;

	public ListItem(final File file) throws Exception {
		if (file != null) {
			this.file = file;
			this.title = file.getName();
		} else {
			throw new Exception("File should not be null");
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * @param file
	 * 		the file to set
	 */
	public void setFile(final File file) {
		this.file = file;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 * 		the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.title;
	}

}
