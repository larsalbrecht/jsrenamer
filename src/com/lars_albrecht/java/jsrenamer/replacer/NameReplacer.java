package com.lars_albrecht.java.jsrenamer.replacer;

import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOption;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [n], [name] tag.
 */
public class NameReplacer extends com.lars_albrecht.java.jsrenamer.replacer.base.BaseReplacer {


	public NameReplacer() {
		super('n', "name");
		this.setOptions(
				new ReplacerOptions()
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
		);
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, ListItem listItem, ListItem originalItem, int itemPos) {
		String  fileName;
		boolean replaced;

		replaced = false;

		fileName = originalItem.getTitle();

		if (matcher.group(3) != null) { // replace [n, <0-9>, <0-9>]
			if ((Integer.parseInt(matcher.group(2)) <= fileName.length())
				&& ((Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(3))) <= fileName.length())
				&& (Integer.parseInt(matcher.group(2)) < (Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(3))))) {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(),
														 fileName.substring(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(3))));
				replaced = true;
			}
		} else if (matcher.group(2) != null) { // replace [n, <0-9>]
			if (Integer.parseInt(matcher.group(2)) <= fileName.length()) {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), fileName.substring(Integer.parseInt(matcher.group(2))));
				replaced = true;
			}
		} else { // replace [n]
			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), fileName);
			replaced = true;
		}

		if (!replaced) { // replace unfound
			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), "");
		}

		return fileNameMask;
		/*
		Pattern p;
		Matcher m;
		String  fileName;
		boolean replaced;

		// \[(name|n)(\W?,\W?(([0-9]+?)\W?,\W?([0-9]+))*|(([0-9]+)*))*\]
		final String pattern = "\\[(name|n)(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))*|(([0-9]+)*))*\\]";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);
		while (m.find()) {
			replaced = false;

			fileName = originalItem.getTitle();

			if (m.group(3) != null) { // replace [n, <0-9>, <0-9>]
				if ((Integer.parseInt(m.group(4)) <= fileName.length())
					&& ((Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))) <= fileName.length())
					&& (Integer.parseInt(m.group(4)) < (Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))))) {
					fileNameMask = fileNameMask.replaceFirst(pattern,
															 fileName.substring(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))));
					replaced = true;
				}
			} else if (m.group(7) != null) { // replace [n, <0-9>]
				if (Integer.parseInt(m.group(7)) <= fileName.length()) {
					fileNameMask = fileNameMask.replaceFirst(pattern, fileName.substring(Integer.parseInt(m.group(7))));
					replaced = true;
				}
			} else { // replace [n]
				fileNameMask = fileNameMask.replaceFirst(pattern, fileName);
				replaced = true;
			}

			if (!replaced) { // replace unfound
				fileNameMask = fileNameMask.replaceFirst(pattern, "");
			}

		}

		return fileNameMask;
		*/

	}
}
