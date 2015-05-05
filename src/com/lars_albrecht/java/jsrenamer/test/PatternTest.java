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
		pt.test1();
		System.out.println("");
//		pt.test2();
		System.out.println("");
//		pt.test3();
		System.out.println("");
		pt.test4();
		System.out.println("");
		pt.test5();
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

	private void test4() {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("Person.of.Interest.S04E01.und.die.im.Lichte.sieht.man.nicht.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E02.Nautilus.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E03.Gefuehl.und.Gewissen.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E04.Beschuetzt.die.Nacht.ihre.Kinder.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E04.Propheten.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E06.Detective.Forge.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");
		testList.add("Person.of.Interest.S04E07.Eine.Kraehe.hackt.der.anderen.ein.Auge.aus.GERMAN.DUBBED.DL.1080p.WebHD.x264-TVP");

		PatternParser pp = new PatternParser(testList, "[HS].[HIS].[S].[D]", "[HS|1] - [HIS|1] - [S|1]");
		pp.parse();
		for (String string : pp.getStrings()) {
			System.out.println(string);
		}
	}

	private void test5() {
		ArrayList<String> testList = new ArrayList<String>();

		testList.add("Bones.S09E01.Booth.und.Bones.und.das.gebrochene.Herz.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E02.Luchs.isst.Luegner.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E03.Wer.war.schlecht.fuer.den.Schlachter.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E04.Pelant.und.die.Goetzendaemmerung.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E05.Ein.Vorbild.als.Vogelfutter.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E06.Die.Frau.in.Weiss.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E07.Flitterwochen.mit.Sonne.Pool.und.totem.Nazi.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E08.Im.Biberdamm.der.Samenmann.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E09.Die.Wut.der.Geschworenen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E10.Grausiger.Geschmacksverstaerker.mit.Geheimnis.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E11.Die.Fetzen.nach.dem.Funken.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E12.Pelants.Raetsel.um.den.Phantommoerder.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E13.Ein.Star.auf.den.Philippinen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E14.Ein.Meister.vor.die.Saeue.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E15.Der.Zeh.und.die.Dazugehoerige.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E16.Letzte.Info.ueber.die.Informantin.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E17.Ungeklaertes.aus.dem.Klaertank.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E18.Tot.ist.die.Karotte.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E19.Phoenix.in.der.Asche.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E20.Zerfallen.zwischen.Baum.und.Borke.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E21.Eine.Eiszeit.vor.dem.Ende.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E22.Ziemlich.boese.Freunde.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E23.Das.Drama.in.der.Queen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");
		testList.add("Bones.S09E24.Bones.und.Booth.in.einem.Bild.der.Zerstoerung.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD");

		PatternParser pp = new PatternParser(testList, "[HS].[HIS].[S].[D]", "[HS|1] - [HIS|1] - [S|1]");
		pp.parse();
		for (String string : pp.getStrings()) {
			System.out.println(string);
		}
	}

}
