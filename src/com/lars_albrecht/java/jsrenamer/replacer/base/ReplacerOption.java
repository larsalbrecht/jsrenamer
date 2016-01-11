package com.lars_albrecht.java.jsrenamer.replacer.base;

import java.util.HashMap;

public class ReplacerOption {

	private Integer                 type;
	private HashMap<String, Object> modifier;

	public ReplacerOption(Integer type) {
		this.type = type;
	}

	public ReplacerOption(Integer type, HashMap<String, Object> modifier) {
		this.type = type;
		this.modifier = modifier;
	}

	public Integer getType() {
		return type;
	}

	@SuppressWarnings("unused")
	public void setType(Integer type) {
		this.type = type;
	}

	public HashMap<String, Object> getModifier() {
		return modifier;
	}

	@SuppressWarnings("unused")
	public void setModifier(HashMap<String, Object> modifier) {
		this.modifier = modifier;
	}
}
