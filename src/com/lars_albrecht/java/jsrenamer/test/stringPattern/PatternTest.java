/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.stringPattern;

import java.util.ArrayList;
import java.util.Arrays;

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

	/**
	 * This testclass is to create an utility to create an automatic replacement.
	 *
	 * ------------
	 * FIRST STEP
	 * ------------
	 * Before:
	 * "Greys.Anatomy.S11E01.Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264.REPACK-euHD"
	 * "Greys.Anatomy.S11E02.Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"
	 *
	 * User puts pattern:
	 * "[S] - [SI] - [T]"
	 *
	 * After:
	 * "Greys Anatomy - S11E01 - Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264.REPACK-euHD"
	 * "Greys Anatomy - S11E02 - Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"
	 *
	 * ------------
	 * SECOND STEP
	 * ------------
	 * Before
	 * "Greys Anatomy - S11E01 - Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264.REPACK-euHD"
	 * "Greys Anatomy - S11E02 - Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"
	 *
	 * User modified pattern like:
	 * "[S] - [SI] - [T][R]"
	 *
	 * After:
	 * "Greys Anatomy - S11E01 - Im.Wind.verloren"
	 * "Greys Anatomy - S11E02 - Das.fehlende.Puzzleteil"
	 *
	 * ------------
	 * THIRD STEP
	 * ------------
	 * Before:
	 * "Greys Anatomy - S11E01 - Im.Wind.verloren"
	 * "Greys Anatomy - S11E02 - Das.fehlende.Puzzleteil"
	 *
	 * User puts in separator "."
	 *
	 * After:
	 * "Greys Anatomy - S11E01 - Im Wind verloren"
	 * "Greys Anatomy - S11E02 - Das fehlende Puzzleteil"
	 */
	public PatternTest() {

		ArrayList<String> testList = new ArrayList<String>();


		testList.add("Greys.Anatomy.S11E01.Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264.REPACK-euHD");
		testList.add("Greys.Anatomy.S11E02.Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E03.Irrtum.ausgeschlossen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E04.Ellis.Grey.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E05.Auszeit.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E06.Familienzusammenfuehrung.GERMAN.DUBBED.DL.720p.WebHD");
		testList.add("Greys.Anatomy.S11E07.Noch.mal.von.vorne.bitte.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E08.Risiko.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E09.Schockzustand.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E10.Der.letzte.Einsiedler.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E11.Hoellenqualen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");


		/*
		testList.add("Dexter - S01E01 - Der Tod kommt in kleinen Stücken");
		testList.add("Dexter - S01E02 - Eiskalt erwischt");
		testList.add("Dexter - S02E01 - Verflucht");
		testList.add("Dexter - S08E06 - Die Sehnsucht nach Schnee");
		testList.add("Dexter - S08E12 - Abschied");
		*/

		// [S] = STRING
		// [SI] = STRING WITH INT
		// [T] = TEMPLATE STRING
		String pattern = "[S] - [SI] - [T]";

		StringEx s = new StringEx();
		s.setStrings(testList);
		s.setPlaceholder('*');
		s.parseStrings(Boolean.TRUE);

		//ArrayList<SubString> subStrings = s.getSubStrings(" - ");
		ArrayList<SubString> subStrings = s.getSubStrings(".");
		System.out.println("------------");
		for (SubString subString : subStrings) {
			System.out.println("------------");
			System.out.println(subString.getSubString());
			System.out.println(subString.getSubString().containsIntPlaceholder());
			System.out.println(subString.isTemplate());
		}

		// Match subStrings to pattern

		String splitter = " - ";
		// create splitted pattern
		ArrayList<String> splittedPattern = new ArrayList<String>(Arrays.asList(pattern.split(splitter)));
		String finalString = "";
		int startIndex = 0;
		int patternIndex = 0;
		for (String string : splittedPattern) {
			String tempStr = "";
			if(string.matches("^\\[S\\]{1}$")){
				for (SubString subString : subStrings.subList(startIndex, subStrings.size())) {
					if(!subString.isTemplate() && !subString.getSubString().containsIntPlaceholder()){
						tempStr += subString + " ";
						startIndex++;
					} else {
						break;
					}
				}
			} else if(string.matches("^\\[SI\\]{1}$")){
				for (SubString subString : subStrings.subList(startIndex, subStrings.size())) {
					if(!subString.isTemplate() && subString.getSubString().containsIntPlaceholder()){
						// TODO replace ints with real ones
						tempStr += subString + " ";
						startIndex++;
					} else {
						break;
					}
				}
			} else if(string.matches("^\\[T\\]{1}$")){
				for (SubString subString : subStrings.subList(startIndex, subStrings.size())) {
					if(subString.isTemplate()){
						// replace this with a real string
						tempStr += subString + " ";
						startIndex++;
					} else {
						break;
					}
				}
			}
			finalString += tempStr.trim();
			if(patternIndex < (splittedPattern.size() - 1)){
				finalString += splitter;
			}
			patternIndex++;
		}
		System.out.println(finalString);
	}

}
