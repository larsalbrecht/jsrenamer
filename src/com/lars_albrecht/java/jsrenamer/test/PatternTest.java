/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * @author albrech
 *
 */
public class PatternTest {

	public static void main(String[] args) {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("Greys.Anatomy.S11E01.Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E02.Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E03.Irrtum.ausgeschlossen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E04.Ellis.Grey.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E05.Auszeit.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E06.Familienzusammenfuehrung.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E07.Noch.mal.von.vorne.bitte.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E08.Risiko.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E09.Schockzustand.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E10.Der.letzte.Einsiedler.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");
		testList.add("Greys.Anatomy.S11E11.Hoellenqualen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD.a");

		String pattern = "[SH] - [SI] - [S][D]";
		String separator = ".";

		PatternParser pp = new PatternParser(testList, pattern, separator);
		pp.parse();
		pp.getStrings();

	}

}
