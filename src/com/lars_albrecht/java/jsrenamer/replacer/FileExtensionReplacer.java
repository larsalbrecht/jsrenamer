package com.lars_albrecht.java.jsrenamer.replacer;

import com.lars_albrecht.java.jsrenamer.model.ListItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [e], [extension] tag.
 */
public class FileExtensionReplacer extends com.lars_albrecht.java.jsrenamer.replacer.base.BaseReplacer {


	public FileExtensionReplacer() {
		super('e', "extension");
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, ListItem listItem, ListItem originalItem, int itemPos) {
		Pattern p;
		Matcher m;

		String fileExtension = null;

		// (\.[^\.]+$)
		p = Pattern.compile("(\\.[^\\.]+$)");
		m = p.matcher(originalItem.getFile().getName());
		while (m.find()) {
			if (m.group(1) != null) {
				fileExtension = m.group(1);
			}
		}

		if (fileExtension != null && !fileExtension.isEmpty()) {
			if (matcher.group(1) != null) { // replace [extension, e]
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), fileExtension);
			}
			return fileNameMask;
		}

		return originalItem.getTitle();
	}
}
