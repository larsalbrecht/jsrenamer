/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.util.ArrayList;

/**
 * @author albrech
 *
 */
public class PatternTest {

	public static void main(String[] args) {
		PatternTest pt = new PatternTest();
		// pt.test1();
		// System.out.println("");
		// pt.test2();
		// System.out.println("");
		pt.test3();
	}

	private void test1() {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("Greys.Anatomy.S11E01.Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E02.Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E03.Irrtum.ausgeschlossen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E04.Ellis.Grey.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E05.Auszeit.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E06.Familienzusammenfuehrung.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E07.Noch.mal.von.vorne.bitte.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E08.Risiko.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E09.Schockzustand.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E10.Der.letzte.Einsiedler.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Greys.Anatomy.S11E11.Hoellenqualen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");

		PatternParser pp = new PatternParser(testList, "[HS].[HIS].[S].[D]", "[HS|1] - [HIS|1] - [S|1]");
		pp.parse();
		for (String string : pp.getStrings()) {
			System.out.println(string);
		}
	}

	private void test2() {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("Sleepy.Hollow.S01E01.Der.kopflose.Reiter.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E02.Blutmond.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E03.Vier.Tage.Schlaf.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E04.Der.Name.der.Bestie.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E05.Die.verlorene.Kolonie.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E06.Geborgte.Zeit.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E07.Der.Mitternachtsritt.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E08.Der.beste.Freund.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E09.Das.Geisterhaus.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E10.Der.Preis.des.Lebens.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E11.Washingtons.Bibel.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E12.Der.Unverzichtbare.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Sleepy.Hollow.S01E13.Hoelle.auf.Erden.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");

		PatternParser pp = new PatternParser(testList, "[HS].[HIS].[S].[D]", "[HS|1] - [HIS|1] - [S|1]");
		pp.parse();
		for (String string : pp.getStrings()) {
			System.out.println(string);
		}
	}

	private void test3() {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("2.Broke.Girls.S02E01.Die.Glueckskette.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E02.Die.Glueckskette.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E03.And.the.Hold.Up.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E04.Cupcake.Wars.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E05.And.the.Pre.Approved.Credit.Card.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E06.And.the.Candy.Manwich.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E07.And.The.Three.Boys.With.Wood.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E08.And.the.Egg.Special.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E09.And.The.New.Boss.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E09.And.The.New.Boss.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E10.And.The.Big.Opening.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E11.And.The.Silent.Partner.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E12.And.the.High.Holidays.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E13.And.the.Bear.Truth.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E14.And.Too.Little.Sleep.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E15.Kristalle.und.Kredite.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E16.And.Just.Plane.Magic.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E17.And.the.Broken.Hip.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E18.And.Not.So.Sweet.Charity.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E19.And.The.Temporary.Distraction.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E20.And.The.Big.Hole.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E21.And.the.Worst.Selfie.Ever.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E22.And.The.Extra.Work.GERMAN.DUBBED.720p.HDTV.x264-TVP");
		testList.add("2.Broke.Girls.S02E23.And.the.Tip.Slip.GERMAN.DUBBED.720p.HDTV.x264-TVP");

		PatternParser pp = new PatternParser(testList, "[HS].[HIS].[S].[D]", "[HS|1] - [HIS|1] - [S|1]");
		pp.parse();
		for (String string : pp.getStrings()) {
			System.out.println(string);
		}
	}

}
