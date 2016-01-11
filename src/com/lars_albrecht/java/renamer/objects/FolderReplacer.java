package com.lars_albrecht.java.renamer.objects;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;
import com.lars_albrecht.java.renamer.core.helper.ReplacerHelper;
import com.lars_albrecht.java.renamer.core.models.ReplacerOption;
import com.lars_albrecht.java.renamer.core.models.ReplacerOptions;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace the [f], [folder] tag.
 */
public class FolderReplacer extends BaseReplacer {


	public FolderReplacer() {
		super('f', "folder");
		this.setOptions(
				new ReplacerOptions()
						// upper directory
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
						// start
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
						// length
						.addOption(
								new ReplacerOption(ReplacerOptions.TYPE_INT)
						)
		);
	}


	@Override
	public String replace(Pattern pattern, Matcher matcher, String fileNameMask, File originalFile, int itemPos) {
		int    folderIndex;
		String folderName;

		if (matcher.group(2) == null) {
			folderIndex = 0;
		} else {
			folderIndex = Integer.parseInt(matcher.group(2));
		}

		folderName = this.getFolderName(originalFile, folderIndex);

		if (matcher.group(4) != null) { // replace [f|<0-9>, <0-9>, <0-9>]
			if ((Integer.parseInt(matcher.group(3)) <= folderName.length())
				&& ((Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(4))) <= folderName.length())
				&& (Integer.parseInt(matcher.group(3)) < (Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(4))))) {
				fileNameMask = fileNameMask
						.replaceFirst(
								pattern.pattern(),
								folderName.substring(Integer.parseInt(matcher.group(3)),
													 Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4))));
			}
		} else if (matcher.group(3) != null) { // replace [f|<0-9>, <0-9>]
			if (Integer.parseInt(matcher.group(3)) <= folderName.length()) {
				fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), folderName.substring(Integer.parseInt(matcher.group(3))));
			}
		} else { // replace [f|<0-9>]
			fileNameMask = fileNameMask.replaceFirst(pattern.pattern(), folderName);
		}

		return fileNameMask;
	}

	/**
	 * Return the folder name of the folder with index folderIndex (reverse from
	 * file).
	 *
	 * @param fileItem
	 * 		File
	 * @param folderIndex
	 * 		Folder index to get
	 *
	 * @return folder Foldername
	 */
	private String getFolderName(final File fileItem, final int folderIndex) {
		final String   path     = ReplacerHelper.getFilepath(fileItem);
		final String[] pathsArr = path.split(Pattern.quote(File.separator));
		if (folderIndex < (pathsArr.length - 1)) {
			ArrayUtils.reverse(pathsArr);
			return pathsArr[folderIndex];
		}
		return "";
	}
}
