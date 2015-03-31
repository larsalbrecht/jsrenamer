/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.stringPattern;

import java.util.ArrayList;

/**
 * @author lalbrecht
 *
 */
public class PatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PatternTest();
	}

	public PatternTest() {

		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Dexter - S01E01 - Der Tod kommt in kleinen Stücken");
		testList.add("Dexter - S01E02 - Eiskalt erwischt");
		testList.add("Dexter - S02E01 - Verflucht");
		testList.add("Dexter - S08E06 - Die Sehnsucht nach Schnee");
		testList.add("Dexter - S08E12 - Abschied");


		StringEx s = new StringEx();
		s.setStrings(testList);
		s.setPlaceholder('*');
		s.parseStrings(Boolean.TRUE);
	}

}
