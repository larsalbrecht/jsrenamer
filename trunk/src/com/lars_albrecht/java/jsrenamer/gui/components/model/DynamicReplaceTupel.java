/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components.model;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * @author lalbrecht
 * 
 */
public class DynamicReplaceTupel {

	private JTextField	fieldA		= null;
	private JTextField	fieldB		= null;
	private JCheckBox	checkField	= null;

	public DynamicReplaceTupel(final JTextField fieldA, final JTextField fieldB, final JCheckBox checkField) {
		this.fieldA = fieldA;
		this.fieldB = fieldB;
		this.checkField = checkField;
	}

	/**
	 * @return the checkField
	 */
	public final JCheckBox getCheckField() {
		return this.checkField;
	}

	/**
	 * @return the fieldA
	 */
	public final JTextField getFieldA() {
		return this.fieldA;
	}

	/**
	 * @return the fieldB
	 */
	public final JTextField getFieldB() {
		return this.fieldB;
	}

	/**
	 * @param checkField
	 *            the checkField to set
	 */
	public final void setCheckField(final JCheckBox checkField) {
		this.checkField = checkField;
	}

	/**
	 * @param fieldA
	 *            the fieldA to set
	 */
	public final void setFieldA(final JTextField fieldA) {
		this.fieldA = fieldA;
	}

	/**
	 * @param fieldB
	 *            the fieldB to set
	 */
	public final void setFieldB(final JTextField fieldB) {
		this.fieldB = fieldB;
	}

}
