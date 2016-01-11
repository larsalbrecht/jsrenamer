package com.lars_albrecht.java.renamer.objects;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;
import com.lars_albrecht.java.renamer.core.models.ReplacerOption;
import com.lars_albrecht.java.renamer.core.models.ReplacerOptions;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [c], [counter] tag.
 */
public class CounterReplacer extends BaseReplacer {


	public CounterReplacer() {
		super('c', "counter");
		ReplacerOptions options = new ReplacerOptions();
		this.setOptions(
				options
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
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
		int           start    = 0;
		int           step     = 1;
		int           intWidth = 0;
		DecimalFormat df;

		if (matcher.group(4) != null) { // replace [c, <0-9>, <0-9>, <0-9>]
			start = (matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : start);
			step = (matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : step);
			intWidth = (matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : intWidth);
			df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), df.format(((itemPos * step) + start)));
		} else if (matcher.group(3) != null) { // replace [c, <0-9>, <0-9>]
			start = (matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : start);
			step = (matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : step);
			df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), df.format(((itemPos * step) + start)));
		} else if (matcher.group(2) != null) { // replace [c, <0-9>]
			start = (matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : start);
			df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), df.format(((itemPos * step) + start)));
		} else { // replace [c]
			df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), df.format(((itemPos * step) + start)));
		}

		return fileNameMask;
	}

	/**
	 * Returns a pattern for the decimal format.
	 *
	 * @param length
	 * 		Length for pattern
	 *
	 * @return New pattern
	 */
	private String getPatternForDecimalFormat(final int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += "0";
		}
		return result;
	}
}
