package com.lars_albrecht.java.renamer.core.controller;

import com.lars_albrecht.java.renamer.core.base.BaseReplacer;
import com.lars_albrecht.java.renamer.objects.*;

import java.io.File;
import java.util.ArrayList;

public class ReplacerController {

	private ArrayList<BaseReplacer> replacerList = null;

	public ReplacerController(){
		this.replacerList = new ArrayList<BaseReplacer>();
		this.replacerList.add(new NameReplacer());
		this.replacerList.add(new FolderReplacer());
		this.replacerList.add(new CounterReplacer());
		this.replacerList.add(new DateReplacer());
		this.replacerList.add(new SizeReplacer());
		this.replacerList.add(new FileExtensionReplacer());
	}

	public String doReplace(String fileNameMask, final File originalFile, final int itemPos) {
		for (BaseReplacer replacer : this.replacerList) {
			fileNameMask = replacer.getReplacement(fileNameMask, originalFile, itemPos);
		}
		return fileNameMask;
	}
}
