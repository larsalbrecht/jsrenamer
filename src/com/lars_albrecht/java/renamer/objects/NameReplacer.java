package com.lars_albrecht.java.renamer.objects;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;
import com.lars_albrecht.java.renamer.core.models.ReplacerOption;
import com.lars_albrecht.java.renamer.core.models.ReplacerOptions;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [n], [name] tag.
 */
public class NameReplacer extends BaseReplacer {


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
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, File originalFile, int itemPos) {
		String  fileName;
		boolean replaced;

		replaced = false;

		fileName = originalFile.getName();

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
	}
}
