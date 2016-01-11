package com.lars_albrecht.java.renamer.objects;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [e], [extension] tag.
 */
public class FileExtensionReplacer extends BaseReplacer {


	public FileExtensionReplacer() {
		super('e', "extension");
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, File originalFile, int itemPos) {
		Pattern p;
		Matcher m;

		String fileExtension = null;

		// (\.[^\.]+$)
		p = Pattern.compile("(\\.[^\\.]+$)");
		m = p.matcher(originalFile.getName());
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

		return originalFile.getName();
	}
}
