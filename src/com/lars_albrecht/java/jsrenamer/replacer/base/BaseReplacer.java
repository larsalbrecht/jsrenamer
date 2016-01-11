package com.lars_albrecht.java.jsrenamer.replacer.base;

import com.lars_albrecht.java.jsrenamer.model.ListItem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.lars_albrecht.java.jsrenamer.replacer.base.ReplacerOptions.*;

public abstract class BaseReplacer {

	protected final static String STRING_START = "[";
	protected final static String STRING_END   = "]";

	protected Character       shortTag = null;
	protected String          longTag  = null;
	protected ReplacerOptions options  = null;
	protected Pattern         pattern  = null;

	/**
	 * @param shortTag
	 * 		The short tag to replace
	 * @param longTag
	 * 		The long tag to replace
	 */
	protected BaseReplacer(final Character shortTag, final String longTag) {
		this.shortTag = shortTag;
		this.longTag = longTag;
	}

	/**
	 * Add ReplacerOptions to replacer.
	 *
	 * @param options
	 * 		ReplacerOptions
	 */
	protected void setOptions(ReplacerOptions options) {
		this.options = options;
	}

	/**
	 * Generates the pattern String for the options (if exists).
	 *
	 * @return options Pattern String
	 */
	private String generateOptionsPatternString() {
		String result = "";
		if (this.options != null && this.options.getOptions() != null && this.options.getOptions().size() > 0) {
			for (ReplacerOption option : this.options.getOptions()) {

				result += "\\W*(?:,\\W*";
				switch (option.getType()) {
					case TYPE_STRING:
						result += "(.*?)";
						break;
					case TYPE_INT:
						result += "([0-9]{1,9})";
						break;
					case TYPE_FLOAT:
						result += "([0-9\\.\\,]+)";
						break;
					case TYPE_DATE:
						result += "(.+)"; // better regex for this?
						break;
					case TYPE_STRINGLIST:
						result += "(";
						if ((Boolean) option.getModifier().get("case-sensitive")) {
							result += "(?i:";
						} else {
							result += "(?:";
						}
						//noinspection unchecked
						result += this.getQuotedJoinedArray("|", (ArrayList<String>) option.getModifier().get("list"));
						result += "))";
						break;
					case TYPE_CHARLIST:
						result += "(";
						if ((Boolean) option.getModifier().get("case-sensitive")) {
							result += "(?i:";
						} else {
							result += "(?:";
						}
						//noinspection unchecked
						result += this.getQuotedJoinedArrayByCharacters("|", (ArrayList<Character>) option.getModifier().get("list"));
						result += "))";
						break;
				}
				if (option.getModifier() != null) {
					if (option.getModifier().containsKey("required") && option.getModifier().get("required") == Boolean.TRUE) {
						result += "){1}";
					} else {
						result += ")?";
					}
				} else {
					result += ")?";
				}
			}
		}

		return result;
	}

	private String getQuotedJoinedArrayByCharacters(String divider, ArrayList<Character> list) {
		ArrayList<String> stringifiedList = new ArrayList<String>();
		for (Character character :
				list) {
			stringifiedList.add(character.toString());

		}
		return getQuotedJoinedArray(divider, stringifiedList);
	}

	/**
	 * Returns a list of quoted strings as single string.
	 *
	 * @param divider
	 * 		The divider to divide.
	 * @param list
	 * 		The list of elements.
	 *
	 * @return String
	 */
	private String getQuotedJoinedArray(String divider, ArrayList<String> list) {
		String            result   = "";
		ArrayList<String> itemList = new ArrayList<String>();
		itemList.addAll(list);
		for (int i = 0; i < itemList.size(); i++) {
			if (i > 0) {
				result += divider;
			}
			result += Pattern.quote(itemList.get(i));
		}
		return result;
	}

	/**
	 * Generates the pattern String with the tag and the options (if exists).
	 * The pattern always start with [, followed by the tags (short and long). The tags itself are case-insensitive.
	 * After that, the options (if available) comes.
	 *
	 * @return pattern as String
	 */
	private String generatePatternString() {
		String patternString;
		String patternStart   = Pattern.quote(BaseReplacer.STRING_START) + "\\W*((?i:" + Pattern.quote(this.shortTag.toString()) + "|" + Pattern.quote(this.longTag) + ")){1}";
		String patternEnd     = "\\W*" + Pattern.quote(BaseReplacer.STRING_END);
		String patternOptions = this.generateOptionsPatternString();

		patternString = patternStart + patternOptions + patternEnd;

		if (this.options != null && this.options.isHasEndtag()) {
			patternString += "(.+?)" + "(?:(?:" + patternStart + patternEnd + ")|(?:$))";
		}

		return patternString;
	}

	/**
	 * Returns the pattern.
	 * The pattern will be generated ONCE. After generating, the pattern will be reused.
	 *
	 * @return Pattern
	 */
	private Pattern getPattern() {
		if (this.pattern == null) {
			Pattern result = null;
			try {
				result = Pattern.compile(this.generatePatternString());
			} catch (PatternSyntaxException exception) {
				//System.err.println(exception.getDescription());
			}
			this.pattern = result;
		}
		return this.pattern;
	}

	/**
	 * Returns the new string.
	 *
	 * @param fileNameMask
	 * 		The typed in string
	 * @param listItem
	 * 		The ListItem
	 * @param originalItem
	 * 		The original ListItem
	 * @param itemPos
	 * 		The position in the list.
	 *
	 * @return new String
	 */
	public String getReplacement(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = this.getPattern();
		if (p != null) {
			Matcher m = p.matcher(fileNameMask);

			while (m.find()) {
				fileNameMask = replace(p, m, fileNameMask, listItem, originalItem, itemPos);
			}
		}


		return fileNameMask;
	}

	/**
	 * Will be called for each replacement found by pattern.
	 *
	 * @param pattern
	 * 		Pattern
	 * @param matcher
	 * 		Matcher
	 * @param fileNameMask
	 * 		The type in string
	 * @param listItem
	 * 		The ListItem
	 * @param originalItem
	 * 		The original ListItem
	 * @param itemPos
	 * 		The position in the list.
	 *
	 * @return single replacement string
	 */
	protected abstract String replace(final Pattern pattern, final Matcher matcher, String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos);

}
