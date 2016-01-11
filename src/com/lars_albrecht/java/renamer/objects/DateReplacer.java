package com.lars_albrecht.java.renamer.objects;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;
import com.lars_albrecht.java.renamer.core.models.ReplacerOption;
import com.lars_albrecht.java.renamer.core.models.ReplacerOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [d], [date] tag.
 */
public class DateReplacer extends BaseReplacer {


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
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, File originalFile, int itemPos) {
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
