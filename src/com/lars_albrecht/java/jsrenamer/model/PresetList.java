/**
 *
 */
package com.lars_albrecht.java.jsrenamer.model;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;
import com.lars_albrecht.java.jsrenamer.helper.CSVHelper;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author lalbrecht
 */
public class PresetList {

	private ArrayList<Preset> presetList  = null;
	private File              presetsFile = null;

	/**
	 * Creates a PresetList. Create a new preset file at presetsFilePath if not
	 * exists. If initOnStartup the file will be loaded directly.
	 *
	 * @param presetsFilePath
	 * 		Path for presets
	 * @param initOnStartup
	 * 		Init presets at startup
	 */
	public PresetList(final String presetsFilePath, final boolean initOnStartup) {
		this.presetsFile = new File(presetsFilePath);
		if (!this.presetsFile.exists()) {
			try {
				//noinspection ResultOfMethodCallIgnored
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
	 * 		The preset to use
	 *
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
				assert writer != null;
				writer.close();
			} catch (final Exception ex) {
				// do nothing
			}
		}
	}

	/**
	 * Returns the preset with the title.
	 *
	 * @param presetTitle
	 * 		The title of the preset
	 *
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
		return this.presetList.toArray(new Preset[this.presetList.size()]);
	}

	/**
	 * Returns an Array of titles.
	 *
	 * @return list of titles
	 */
	public String[] getTitles() {
		final String[] presetTitleList = new String[this.presetList.size()];
		final int      i               = 0;
		for (final Preset preset : this.presetList) {
			presetTitleList[i] = preset.getTitle();
		}
		return presetTitleList;
	}

	public int indexOf(final Preset preset) {
		return this.presetList.indexOf(preset);
	}

	@SuppressWarnings("unused")
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

		BufferedReader reader    = null;
		final String   separator = ",";

		try {
			String                            line;
			String[]                          lineArr;
			ArrayList<DynamicInputCheckTupel> tupelList;
			DynamicInputCheckTupel            tempTupel = null;
			JCheckBox                         temp;
			Preset                            tempPreset;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.presetsFile), Charset.forName("UTF-8")));
			int tupelIndex;
			int counter = 0;
			while ((line = reader.readLine()) != null) {
				tupelIndex = 0;
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
							} else {
								tupelIndex++;
							}
							switch (tupelIndex) {
								case 0:
									tempTupel.setFieldAStart(new JTextField(string));
									break;
								case 1:
									temp = new JCheckBox();
									temp.setSelected(Boolean.parseBoolean(string));
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
									temp.setSelected(Boolean.parseBoolean(string));
									tempTupel.setFieldAEndCheck(temp);
									break;
								case 5:
									tempTupel.setFieldB(new JTextField(string));
									break;
								case 6:
									temp = new JCheckBox();
									temp.setSelected(Boolean.parseBoolean(string));
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
				tempPreset.setDynamicInputList(tupelList);
				resultList.add(tempPreset);
			}
			//noinspection StatementWithEmptyBody
			if (counter == 0) {
				//System.out.println("No presets");
			}
		} catch (final IOException ex) {
			// report
		} finally {
			try {
				assert reader != null;
				reader.close();
			} catch (final Exception ex) {
				// do nothing
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
	 * 		The list of presets
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
	 * 		The name of the preset
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
	 * 		The preset
	 * @param presetFile
	 * 		The preset file
	 */
	private void savePreset(final Preset preset, final File presetFile) {
		Writer       writer    = null;
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
				assert writer != null;
				writer.close();
			} catch (final Exception ex) {
				// do nothing
			}
		}
	}

	public int size() {
		return this.presetList.size();
	}

}
