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
import java.io.PrintWriter;
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

	/**
	 * Creates a PresetList. Create a new preset file at presetsFilePath if not
	 * exists. If initOnStartup the file will be loaded directly.
	 * 
	 * @param presetsFilePath
	 * @param initOnStartup
	 */
	public PresetList(final String presetsFilePath, final boolean initOnStartup) {
		this.presetsFile = new File(presetsFilePath);
		if (!this.presetsFile.exists()) {
			try {
				this.presetsFile.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		if (initOnStartup) {
			this.presetList = this.load();
		} else {
			this.presetList = new ArrayList<Preset>();
		}
	}

	/**
	 * Adds a preset to the list.
	 * 
	 * @param preset
	 * @return this
	 */
	public PresetList add(final Preset preset) {
		this.presetList.add(preset);

		return this;
	}

	/**
	 * Clears a preset file.
	 */
	private void clearPresetFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(this.presetsFile);
		} catch (final IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (final Exception ex) {
			}
		}
	}

	/**
	 * Returns the preset with the title.
	 * 
	 * @param title
	 * @return Preset
	 */
	public Preset get(final String presetTitle) {
		for (final Preset preset : this.presetList) {
			if (preset.getTitle().equals(presetTitle)) {
				return preset;
			}
		}
		return null;
	}

	public Preset[] getPresets() {
		return this.presetList.toArray(new Preset[0]);
	}

	/**
	 * Returns an Array of titles.
	 * 
	 * @return list of titles
	 */
	public String[] getTitles() {
		final String[] presetTitleList = new String[this.presetList.size()];
		final int i = 0;
		for (final Preset preset : this.presetList) {
			presetTitleList[i] = preset.getTitle();
		}
		return presetTitleList;
	}

	public int indexOf(final Preset preset) {
		return this.presetList.indexOf(preset);
	}

	public int indexOf(final String presetTitle) {
		int i = 0;
		for (final Preset preset : this.presetList) {
			if (preset.getTitle().equals(presetTitle)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * Loads a preset file and returns the presets.
	 * 
	 * @return ArrayList<Preset> loadedPresets
	 */
	public ArrayList<Preset> load() {
		final ArrayList<Preset> resultList = new ArrayList<Preset>();

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
				System.out.println("new line");
				tupelIndex = 0;
				counter++;
				lineArr = CSVHelper.parseToRead(separator, line);
				System.out.println("Parts: " + lineArr.length);
				tempPreset = new Preset(null);
				tupelList = new ArrayList<DynamicInputCheckTupel>();
				int i = 0;
				for (final String string : lineArr) {
					System.out.println("new string part");
					switch (i) {
						case 0:
							tempPreset.setTitle(string);
							break;
						case 1:
							tempPreset.setNameInput(string);
							break;
						default:
							if (tempTupel == null) {
								System.out.println("create new tupel");
								tempTupel = new DynamicInputCheckTupel(null, null, null, null, null, null, null);
								tupelIndex = 0;
							} else {
								System.out.println("add one index");
								tupelIndex++;
							}
							switch (tupelIndex) {
								case 0:
									tempTupel.setFieldAStart(new JTextField(string));
									System.out.println("0");
									break;
								case 1:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setFieldAStartCheck(temp);
									System.out.println("1");
									break;
								case 2:
									tempTupel.setFieldA(new JTextField(string));
									System.out.println("2");
									break;
								case 3:
									tempTupel.setFieldAEnd(new JTextField(string));
									System.out.println("3");
									break;
								case 4:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setFieldAEndCheck(temp);
									System.out.println("4");
									break;
								case 5:
									tempTupel.setFieldB(new JTextField(string));
									System.out.println("5");
									break;
								case 6:
									temp = new JCheckBox();
									temp.setEnabled(Boolean.parseBoolean(string));
									tempTupel.setCheckField(temp);
									tupelIndex = 0;
									tupelList.add(tempTupel);
									tempTupel = null;
									System.out.println("6");
									break;
							}
							break;
					}
					i++;
				}
				tempPreset.setDynamicInputList(tupelList);
				System.out.println("Size: " + tempPreset.getDynamicInputList().size());
				System.out.println(tempPreset.getDynamicInputList().get(0));
				resultList.add(tempPreset);
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
		return resultList;
	}

	public boolean remove(final Preset preset) {
		return this.presetList.remove(preset);
	}

	public boolean replace(final Preset oldPreset, final Preset newPreset) {
		if (this.indexOf(oldPreset) > -1) {
			this.presetList.set(this.indexOf(oldPreset), newPreset);
			return true;
		}
		return false;
	}

	public void save() {
		this.save(this.presetList);
	}

	/**
	 * Saves a list of presets. Clears the preset file before saving
	 * (appending).
	 * 
	 * @param presetList
	 */
	private void save(final ArrayList<Preset> presetList) {
		this.clearPresetFile();
		for (final Preset preset : presetList) {
			this.savePreset(preset, this.presetsFile);
		}
	}

	/**
	 * Resave single preset. Loads the current file and overwrite it with the
	 * single preset.
	 * 
	 * @param presetTitle
	 */
	public void save(final String presetTitle) {
		final ArrayList<Preset> tempList = this.load();
		for (int i = 0; i < this.presetList.size(); i++) {
			if (this.presetList.get(i).getTitle().equals(presetTitle)) {
				tempList.set(i, this.presetList.get(i));
				System.out.println("found at " + i);
				break;
			}
		}
		this.save(tempList);
	}

	/**
	 * Saves a preset. Opens the file and append the preset to the end.
	 * 
	 * @param preset
	 * @param presetFile
	 */
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
