package com.lars_albrecht.java.renamer.core.models;

import java.util.ArrayList;

public class ReplacerOptions {

	public static final int TYPE_STRING     = 0;
	public static final int TYPE_INT        = 1;
	public static final int TYPE_FLOAT      = 2;
	public static final int TYPE_DATE       = 3;
	public static final int TYPE_STRINGLIST = 4;
	public static final int TYPE_CHARLIST   = 5;
	private ArrayList<ReplacerOption> options   = new ArrayList<ReplacerOption>();
	private boolean                   hasEndtag = false;

	public ReplacerOptions() {
	}

	public ReplacerOptions(final boolean hasEndtag) {
		this.hasEndtag = hasEndtag;
	}

	public ReplacerOptions addOption(final ReplacerOption option) {
		this.options.add(option);

		return this;
	}

	public boolean isHasEndtag() {
		return hasEndtag;
	}

	@SuppressWarnings("unused")
	public void setHasEndtag(boolean hasEndtag) {
		this.hasEndtag = hasEndtag;
	}

	public ArrayList<ReplacerOption> getOptions() {
		return options;
	}
}
