/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.components.model;

import javax.swing.*;
import java.io.Serializable;

/**
 * @author lalbrecht
 */
public class DynamicInputCheckTupel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8518227484048706672L;

	private JTextField fieldA           = null;
	private JTextField fieldAStart      = null;
	private JCheckBox  fieldAStartCheck = null;
	private JTextField fieldAEnd        = null;
	private JCheckBox  fieldAEndCheck   = null;
	private JTextField fieldB           = null;
	private JCheckBox  checkField       = null;

	public DynamicInputCheckTupel(final JTextField fieldA, final JTextField fieldAStart, final JCheckBox fieldAStartCheck,
								  final JTextField fieldAEnd, final JCheckBox fieldAEndCheck, final JTextField fieldB, final JCheckBox checkField) {
		this.fieldA = fieldA;
		this.fieldAStart = fieldAStart;
		this.fieldAStartCheck = fieldAStartCheck;
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
	 * @param checkField
	 * 		the checkField to set
	 */
	public final void setCheckField(final JCheckBox checkField) {
		this.checkField = checkField;
	}

	/**
	 * @return the fieldA
	 */
	public final JTextField getFieldA() {
		return this.fieldA;
	}

	/**
	 * @param fieldA
	 * 		the fieldA to set
	 */
	public final void setFieldA(final JTextField fieldA) {
		this.fieldA = fieldA;
	}

	/**
	 * @return the fieldAEnd
	 */
	public final JTextField getFieldAEnd() {
		return this.fieldAEnd;
	}

	/**
	 * @param fieldAEnd
	 * 		the fieldAEnd to set
	 */
	public final void setFieldAEnd(final JTextField fieldAEnd) {
		this.fieldAEnd = fieldAEnd;
	}

	/**
	 * @return the fieldAEndCheck
	 */
	public final JCheckBox getFieldAEndCheck() {
		return this.fieldAEndCheck;
	}

	/**
	 * @param fieldAEndCheck
	 * 		the fieldAEndCheck to set
	 */
	public final void setFieldAEndCheck(final JCheckBox fieldAEndCheck) {
		this.fieldAEndCheck = fieldAEndCheck;
	}

	/**
	 * @return the fieldAStart
	 */
	public final JTextField getFieldAStart() {
		return this.fieldAStart;
	}

	/**
	 * @param fieldAStart
	 * 		the fieldAStart to set
	 */
	public final void setFieldAStart(final JTextField fieldAStart) {
		this.fieldAStart = fieldAStart;
	}

	/**
	 * @return the fieldAStartCheck
	 */
	public final JCheckBox getFieldAStartCheck() {
		return this.fieldAStartCheck;
	}

	/**
	 * @param fieldAStartCheck
	 * 		the fieldAStartCheck to set
	 */
	public final void setFieldAStartCheck(final JCheckBox fieldAStartCheck) {
		this.fieldAStartCheck = fieldAStartCheck;
	}

	/**
	 * @return the fieldB
	 */
	public final JTextField getFieldB() {
		return this.fieldB;
	}

	/**
	 * @param fieldB
	 * 		the fieldB to set
	 */
	public final void setFieldB(final JTextField fieldB) {
		this.fieldB = fieldB;
	}

}
