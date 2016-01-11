package com.lars_albrecht.java.jsrenamer.replacer;

import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOption;
import com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [s], [size] tag.
 */
public class SizeReplacer extends com.lars_albrecht.java.jsrenamer.replacer.base.BaseReplacer {


	public SizeReplacer() {
		super('s', "size");
		this.setOptions(
				new ReplacerOptions(true)
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_CHARLIST, new HashMap<String, Object>() {{
									put("required", Boolean.TRUE);
									put("case-sensitive", Boolean.FALSE);
									put("list", new ArrayList<Character>() {
										{
											add('u');
											add('l');
										}
									});
								}})
						)
		);
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, ListItem listItem, ListItem originalItem, int itemPos) {
		if (matcher.group(3) != null) { // replace [s, size]
			if (matcher.group(2).equals("u")) {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), matcher.group(3).toUpperCase());
			} else if (matcher.group(2).equals("l")) {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), matcher.group(3).toLowerCase());
			} else {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), matcher.group(3));
			}
		}

		return fileNameMask;
		
		/*Pattern p;
		Matcher m;

		// \[(size|s)((\W?,\W?([u|l]))*\](.+?)(?=((\[(size|s)((\W?,\W?([u|l]))*\]))|$)))
		// \[\W*(s|size){1}\W*(?:,\W*([u|l]))\W*\]?.*?(?=\[\W*(s|size){1}\W*(?:,\W*([u|l]))\W*\])
		p = Pattern.compile("\\[(size|s)((\\W?,\\W?([u|l]))*\\](.+?)(?=((\\[(size|s)((\\W?,\\W?([u|l]))*\\]))|$)))");
		m = p.matcher(fileNameMask);

		while (m.find()) {
			if (m.group(4) != null) { // replace [s, size]
				if (m.group(4).equals("u")) {
					fileNameMask = fileNameMask.replaceFirst(p.pattern(), m.group(5).toUpperCase());
				} else if (m.group(4).equals("l")) {
					fileNameMask = fileNameMask.replaceFirst(p.pattern(), m.group(5).toLowerCase());
				} else {
					fileNameMask = fileNameMask.replaceFirst(p.pattern(), m.group(5));
				}
			} else if (m.group(5) != null) {
				fileNameMask = fileNameMask.replaceFirst(p.pattern(), m.group(5));
			}
		}

		return fileNameMask;*/
	}
}
