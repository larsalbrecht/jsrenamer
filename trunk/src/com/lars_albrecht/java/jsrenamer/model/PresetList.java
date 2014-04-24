/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;
import com.lars_albrecht.java.jsrenamer.helper.CSVHelper;

/**
 * @author lalbrecht
 * 
 */
public class PresetList {

	private ArrayList<Preset>	presetList	= null;
	private File				presetsFile	= null;

	public PresetList(final String presetsFolderStr, final boolean initOnStartup) {
		this.presetList = new ArrayList<Preset>();
		this.presetsFile = new File(presetsFolderStr);
		if (!this.presetsFile.exists()) {
			try {
				this.presetsFile.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		if (initOnStartup) {
			this.load();
		}
	}

	public PresetList add(final Preset preset) {
		this.presetList.add(preset);

		return this;
	}

	public Preset get(final String title) {
		for (final Preset preset : this.presetList) {
			if (preset.getTitle().equals(title)) {
				return preset;
			}
		}
		return null;
	}

	public String[] getTitles() {
		System.out.println(this.presetList.size());
		final String[] presetTitleList = new String[this.presetList.size()];
		final int i = 0;
		for (final Preset preset : this.presetList) {
			presetTitleList[i] = preset.getTitle();
		}
		return presetTitleList;
	}

	public void load() {
		BufferedReader reader = null;
		final String separator = ",";

		try {
			String line = null;
			String[] lineArr = null;
			ArrayList<DynamicInputCheckTupel> tupelList = null;
			DynamicInputCheckTupel tempTupel = null;
			JCheckBox temp = null;
			Preset tempPreset;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.presetsFile), Charset.forName("UTF-8")));
			int tupelIndex = 0;
			int counter = 0;
			while ((line = reader.readLine()) != null) {
				counter++;
				lineArr = CSVHelper.parseToRead(separator, line);
				tempPreset = new Preset(null);
				tupelList = new ArrayList<DynamicInputCheckTupel>();
				int i = 0;
				for (final String string : lineArr) {
					switch (i) {
						case 0:
							tempPreset.setTitle(string);
							break;
						case 1:
							tempPreset.setNameInput(string);
							break;
						default:
							if (tempTupel == null) {
								tempTupel = new DynamicInputCheckTupel(null, null, null, null, null, null, null);
								tupelIndex = 0;
							}
							switch (tupelIndex) {
								case 0:
									tempTupel.setFieldAStart(new JTextField(string));
									break;
								case 1:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setFieldAStartCheck(temp);
									break;
								case 2:
									tempTupel.setFieldA(new JTextField(string));
									break;
								case 3:
									tempTupel.setFieldAEnd(new JTextField(string));
									break;
								case 4:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setFieldAEndCheck(temp);
									break;
								case 5:
									tempTupel.setFieldB(new JTextField(string));
									break;
								case 6:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setCheckField(temp);
									tupelIndex = 0;
									tupelList.add(tempTupel);
									tempTupel = null;
									break;
							}
							break;
					}

					i++;
				}
				System.out.println("add new preset with name: " + tempPreset.getTitle());
				tempPreset.setDynamicInputList(tupelList);
				this.presetList.add(tempPreset);
			}
			if (counter == 0) {
				System.out.println("No presets");
			}
		} catch (final IOException ex) {
			// report
		} finally {
			try {
				reader.close();
			} catch (final Exception ex) {
			}
		}
	}

	public void save() {
		for (final Preset preset : this.presetList) {
			this.savePreset(preset, this.presetsFile);
		}
	}

	public void save(final String title) {
		for (final Preset preset : this.presetList) {
			if (preset.getTitle().equals(title)) {
				this.savePreset(preset, this.presetsFile);
			}
		}
	}

	private void savePreset(final Preset preset, final File presetFile) {
		Writer writer = null;
		final String separator = ",";

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(presetFile, true), Charset.forName("UTF-8")));
			final String[] options = new String[((2 + ((preset.getDynamicInputList().size()) * 7)))];
			options[0] = preset.getTitle();
			options[1] = preset.getNameInput();
			int counter = 2;
			for (final DynamicInputCheckTupel tupel : preset.getDynamicInputList()) {
				options[counter] = tupel.getFieldAStart().getText();
				options[++counter] = tupel.getFieldAStartCheck().isSelected() ? "TRUE" : "FALSE";
				options[++counter] = tupel.getFieldA().getText();
				options[++counter] = tupel.getFieldAEnd().getText();
				options[++counter] = tupel.getFieldAEndCheck().isSelected() ? "TRUE" : "FALSE";
				options[++counter] = tupel.getFieldB().getText();
				options[++counter] = tupel.getCheckField().isSelected() ? "TRUE" : "FALSE";
				counter++;
			}
			writer.write(CSVHelper.parseToWrite(separator, options));

		} catch (final IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (final Exception ex) {
			}
		}
	}

}
