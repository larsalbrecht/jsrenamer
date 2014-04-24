/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.model;

import java.util.ArrayList;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;

/**
 * @author lalbrecht
 * 
 */
public class Preset {

	private String								title				= null;
	private String								nameInput			= null;
	private ArrayList<DynamicInputCheckTupel>	dynamicInputList	= null;

	private boolean								modified			= false;

	public Preset(final String title) {
		this.title = title;
		this.dynamicInputList = new ArrayList<DynamicInputCheckTupel>();
	}

	public Preset(final String title, final String nameInput, final ArrayList<DynamicInputCheckTupel> dynamicInputList) {
		this.title = title;
		this.nameInput = nameInput;
		if (dynamicInputList == null) {
			this.dynamicInputList = new ArrayList<DynamicInputCheckTupel>();
		} else {
			this.dynamicInputList = dynamicInputList;
		}
	}

	/**
	 * @return the dynamicInputList
	 */
	public final ArrayList<DynamicInputCheckTupel> getDynamicInputList() {
		return this.dynamicInputList;
	}

	/**
	 * @return the nameInput
	 */
	public final String getNameInput() {
		return this.nameInput;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return this.title;
	}

	boolean isModified() {
		return this.modified;
	}

	/**
	 * @param dynamicInputList
	 *            the dynamicInputList to set
	 */
	public final void setDynamicInputList(final ArrayList<DynamicInputCheckTupel> dynamicInputList) {
		this.dynamicInputList = dynamicInputList;
	}

	public void setIsModified() {
		this.modified = true;
	}

	/**
	 * @param nameInput
	 *            the nameInput to set
	 */
	public final void setNameInput(final String nameInput) {
		this.nameInput = nameInput;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(final String title) {
		this.title = title;
	}

}
