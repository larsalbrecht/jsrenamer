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
public class DynamicInputCheckTupel {

	private JTextField	fieldA			= null;
	private JTextField	fieldAEnd		= null;
	private JCheckBox	fieldAEndCheck	= null;
	private JTextField	fieldB			= null;
	private JCheckBox	checkField		= null;

	public DynamicInputCheckTupel(final JTextField fieldA, final JTextField fieldAEnd, final JCheckBox fieldAEndCheck,
			final JTextField fieldB, final JCheckBox checkField) {
		this.fieldA = fieldA;
		this.fieldAEnd = fieldAEnd;
		this.fieldAEndCheck = fieldAEndCheck;
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
	 * @return the fieldAEnd
	 */
	public final JTextField getFieldAEnd() {
		return this.fieldAEnd;
	}

	/**
	 * @return the fieldAEndCheck
	 */
	public final JCheckBox getFieldAEndCheck() {
		return this.fieldAEndCheck;
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
	 * @param fieldAEnd
	 *            the fieldAEnd to set
	 */
	public final void setFieldAEnd(final JTextField fieldAEnd) {
		this.fieldAEnd = fieldAEnd;
	}

	/**
	 * @param fieldAEndCheck
	 *            the fieldAEndCheck to set
	 */
	public final void setFieldAEndCheck(final JCheckBox fieldAEndCheck) {
		this.fieldAEndCheck = fieldAEndCheck;
	}

	/**
	 * @param fieldB
	 *            the fieldB to set
	 */
	public final void setFieldB(final JTextField fieldB) {
		this.fieldB = fieldB;
	}

}
