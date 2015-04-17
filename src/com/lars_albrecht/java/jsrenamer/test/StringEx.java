/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.util.ArrayList;

import com.lars_albrecht.java.jsrenamer.test.CharacterEx;

/**
 * @author lalbrecht
 *
 */
public class StringEx {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_HARDSTRING = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_STRINGINTEGER = 2;
	public static final int TYPE_DUMMY = 3;

	private String string = null;

	// TODO fill only when needed
	private ArrayList<CharacterEx> characterList = null;

	private Integer type = TYPE_UNKNOWN;

	public StringEx(final String string, final Integer type) {
		this.string = string;
		if(type > TYPE_DUMMY || type < TYPE_UNKNOWN){
			this.type = TYPE_UNKNOWN;
		} else {
			this.type = type;
		}
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}



}
