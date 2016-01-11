package com.lars_albrecht.java.jsrenamer.replacer;

import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOption;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [d], [date] tag.
 */
public class DateReplacer extends com.lars_albrecht.java.jsrenamer.replacer.base.BaseReplacer {


	public DateReplacer() {
		super('d', "date");
		this.setOptions(
				new ReplacerOptions()
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_DATE)
						)
		);
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, ListItem listItem, ListItem originalItem, int itemPos) {
		final String           origDatePattern = "yyyy-MM-d";
		String                 datePattern     = origDatePattern;
		final SimpleDateFormat sdfmt           = new SimpleDateFormat();

		if (matcher.group(2) != null) { // get date pattern
			datePattern = matcher.group(2);
		}

		try {
			sdfmt.applyPattern(datePattern);
		} catch (final Exception e) {
			sdfmt.applyPattern(origDatePattern);
		}
		fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), sdfmt.format(new Date()));

		return fileNameMask;
	}
}
