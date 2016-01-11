/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui;

import com.lars_albrecht.java.jsrenamer.gui.components.AdvancedInputField;
import com.lars_albrecht.java.jsrenamer.gui.components.DynamicInputCheckPanel;
import com.lars_albrecht.java.jsrenamer.gui.components.MenuButton;
import com.lars_albrecht.java.jsrenamer.gui.components.OptionPanel;
import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;
import com.lars_albrecht.java.jsrenamer.gui.components.renderer.ListItemListCellRenderer;
import com.lars_albrecht.java.jsrenamer.gui.handler.FileTransferHandler;
import com.lars_albrecht.java.jsrenamer.core.helper.FileHelper;
import com.lars_albrecht.java.jsrenamer.core.helper.PropertiesHelper;
import com.lars_albrecht.java.jsrenamer.core.model.ListItem;
import com.lars_albrecht.java.jsrenamer.core.model.Preset;
import com.lars_albrecht.java.jsrenamer.core.model.PresetList;
import com.lars_albrecht.java.jsrenamer.core.objects.ArrayListEvent;
import com.lars_albrecht.java.jsrenamer.core.objects.EventArrayList;
import com.lars_albrecht.java.jsrenamer.core.objects.IArrayListEventListener;
import com.lars_albrecht.java.renamer.core.controller.ReplacerController;
import com.lars_albrecht.java.renamer.core.helper.ReplacerHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author lalbrecht
 */
public class RenameWindow extends JFrame implements IArrayListEventListener<ListItem>, DocumentListener, ActionListener {

	/**
	 *
	 */
	private static final long                       serialVersionUID         = 1L;
	private              PresetList                 presetList               = null;
	@SuppressWarnings("FieldCanBeLocal")
	private              JList<ListItem>            originalList             = null;
	@SuppressWarnings("FieldCanBeLocal")
	private              JList<ListItem>            previewList              = null;
	private              DefaultListModel<ListItem> originalListModel        = null;
	private              DefaultListModel<ListItem> previewListModel         = null;
	private              EventArrayList<ListItem>   allList                  = null;
	private              AdvancedInputField         fileNameInput            = null;
	@SuppressWarnings("FieldCanBeLocal")
	private              OptionPanel                optionPanel              = null;
	private              DynamicInputCheckPanel     dynamicReplaceFields     = null;
	private              JButton                    renameButton             = null;
	@SuppressWarnings("FieldCanBeLocal")
	private              MenuButton                 presetButton             = null;
	private              JMenuItem                  saveAsNewPreset          = null;
	private              JMenuItem                  overwritePreset          = null;
	private              JMenuItem                  setAsDefaultPreset       = null;
	private              JMenuItem                  deletePreset             = null;
	private              JMenuItem                  resetCurrent             = null;
	private              JSplitPane                 splitPane                = null;
	@SuppressWarnings("FieldCanBeLocal")
	private              JTextArea                  nameItemList             = null;
	// ** MENU ITEMS **//
	private              JMenuItem                  miSettingSwitchFileSplit = null;

	private ReplacerController replacerController = null;

	public RenameWindow() {
		super("JSRenamer");
		this.presetList = new PresetList("presets", true);
		this.allList = new EventArrayList<ListItem>();
		this.allList.addListener(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setSize(700, 400);
		// center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		PropertiesHelper.loadProperties();

		final String defaultPresetTitle = PropertiesHelper.getProperties().getProperty("presets.default");
		Preset       defaultPreset      = null;
		if (this.presetList.get(defaultPresetTitle) != null) {
			defaultPreset = this.presetList.get(defaultPresetTitle);
		}

		this._initForms(defaultPreset);
		this._initData();

		this.updatePresetButton();
		this.setVisible(true);
		this.fileNameInput.requestFocus();
	}

	public static Object[] prepend(final Object[] oldArray, final Object o) {
		final Object[] newArray = (Object[]) Array.newInstance(oldArray.getClass().getComponentType(), oldArray.length + 1);
		System.arraycopy(oldArray, 0, newArray, 1, oldArray.length);
		newArray[0] = o;
		return newArray;
	}

	/**
	 * Initialize the data (renamer).
	 */
	private void _initData() {
		this.replacerController = new ReplacerController();
	}

	/**
	 * Initialize the form data.
	 *
	 * @param preset
	 * 		The preset to use
	 */
	private void _initForms(final Preset preset) {
		final GridBagLayout gbl = new GridBagLayout();
		this.getContentPane().setLayout(gbl);

		this.initMenu();
		this.initInput();
		this.initDynamicInput();
		this.initOptionPanel();
		this.initLists();
		this.initBottomBar();

		if (preset != null) {
			this.setValuesFromPreset(preset);
		}

		this.setTransferHandler(new FileTransferHandler(this.allList));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.renameButton) {
			this.onRename();
		} else if (e.getSource() == this.saveAsNewPreset) {
			this.onSaveAsNewPreset();
		} else if (e.getSource() == this.overwritePreset) {
			this.onOverwritePreset();
		} else if (e.getSource() == this.setAsDefaultPreset) {
			this.onSetAsDefaultPreset();
		} else if (e.getSource() == this.deletePreset) {
			this.onDeletePreset();
		} else if (e.getSource() == this.resetCurrent) {
			this.resetCurrentPreset(false);
		} else if (e.getSource() == this.miSettingSwitchFileSplit) {
			if (this.splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
				this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			} else {
				this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			}
			this.splitPane.setDividerLocation(.5);
		} else {
			this.updatePreviewList();
		}
	}

	@Override
	public void arrayListAddAll(final ArrayListEvent<ListItem> e) {
		for (final ListItem item : this.allList) {
			try {
				this.originalListModel.addElement(item);
				this.previewListModel.addElement((ListItem) item.clone());
			} catch (final CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void arrayListChanged(final ArrayListEvent<ListItem> ale) {
		this.updatePreviewList();
	}

	@Override
	public void arrayListCleared(final ArrayListEvent<ListItem> e) {
		this.originalListModel.removeAllElements();
		this.previewListModel.removeAllElements();
	}

	@Override
	public void arrayListItemAdded(final ArrayListEvent<ListItem> e) {
		try {
			final ArrayList<ListItem> tempList = e.getItems();
			for (final ListItem item : tempList) {
				this.originalListModel.addElement(item);
				this.previewListModel.addElement((ListItem) item.clone());
			}
		} catch (final CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void arrayListItemRemoved(final ArrayListEvent<ListItem> e) {
		final ArrayList<ListItem> tempList = e.getItems();
		for (final Object item : tempList) {
			this.originalListModel.removeElement(item);
			this.previewListModel.removeElement(item);
		}
	}

	@Override
	public void changedUpdate(final DocumentEvent e) {
	}

	/**
	 * Create a new preset with the current configuration.
	 *
	 * @param presetTitle
	 * 		name for preset
	 *
	 * @return new Preset
	 */
	private Preset createCurrentPreset(final String presetTitle) {
		return new Preset(presetTitle, this.fileNameInput.getText(), this.dynamicReplaceFields.getFieldList());
	}

	private void documentChanged(final DocumentEvent e) {
		if (e.getDocument() == this.fileNameInput.getDocument()) {
			this.updatePreviewList();
		} else if (this.dynamicReplaceFields.hasDocument(e.getDocument())) {
			this.updatePreviewList();
		}
	}

	/**
	 * Initialize and add the bottom bar (the bar with the buttons for preset and rename).
	 */
	private void initBottomBar() {
		final GridBagConstraints gbc = new GridBagConstraints();

		this.presetButton = new MenuButton("Preset");
		this.presetButton.setToolTipText("Presets");

		this.saveAsNewPreset = new JMenuItem("Save as new preset");
		this.overwritePreset = new JMenuItem("Overwrite preset");
		this.setAsDefaultPreset = new JMenuItem("Set as default preset");
		this.deletePreset = new JMenuItem("Delete preset");
		this.resetCurrent = new JMenuItem("Reset");

		this.saveAsNewPreset.setToolTipText("Create a new preset with the current configuration for later use.");
		this.overwritePreset.setToolTipText("Overwrite an existing preset with the current configuration (cannot be undone).");
		this.setAsDefaultPreset.setToolTipText("Set a preset as default preset to load on startup.");
		this.deletePreset.setToolTipText("Delete a preset (cannot be undone).");
		this.resetCurrent.setToolTipText("Resets the current configuration (cannot be undone)");

		this.saveAsNewPreset.addActionListener(this);
		this.overwritePreset.addActionListener(this);
		this.setAsDefaultPreset.addActionListener(this);
		this.deletePreset.addActionListener(this);
		this.resetCurrent.addActionListener(this);

		this.presetButton.addMenuItem(this.saveAsNewPreset);
		this.presetButton.addMenuItem(this.overwritePreset);
		this.presetButton.addMenuItem(this.setAsDefaultPreset);
		this.presetButton.addMenuItem(this.deletePreset);
		this.presetButton.addMenuItem(this.resetCurrent);

		this.presetButton.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.PAGE_END;

		this.getContentPane().add(this.presetButton, gbc);

		this.renameButton = new JButton("Rename");
		this.renameButton.setToolTipText("Renames the files in the list with the current configuration.");
		final Font bFont    = this.renameButton.getFont();
		final Font newBFont = new Font(bFont.getName(), Font.BOLD, bFont.getSize() + 1);
		this.renameButton.setFont(newBFont);
		this.renameButton.addActionListener(this);
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.PAGE_END;
		this.getContentPane().add(this.renameButton, gbc);
	}

	/**
	 * Initialize and add the dynamic input to the frame.
	 */
	private void initDynamicInput() {
		this.dynamicReplaceFields = new DynamicInputCheckPanel("Add", 1, this, this);
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;

		this.getContentPane().add(this.dynamicReplaceFields, gbc);
	}

	/**
	 * Initilize and add the option panel.
	 */
	private void initOptionPanel() {
		this.optionPanel = new OptionPanel(this);
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = .5;
		gbc.weighty = 0;
		gbc.gridwidth = 4;
		gbc.gridheight = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;

		this.getContentPane().add(this.optionPanel, gbc);
	}

	/**
	 * Initialize and add the name input to the frame.
	 */
	private void initInput() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .1;
		gbc.weighty = 0;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.fileNameInput = new AdvancedInputField();
		this.fileNameInput.getDocument().addDocumentListener(this);

		this.getContentPane().add(this.fileNameInput, gbc);
	}

	/**
	 * Initialize and add the lists to the frame.
	 */
	private void initLists() {
		this.originalListModel = new DefaultListModel<ListItem>();
		this.previewListModel = new DefaultListModel<ListItem>();

		this.originalList = new JList<ListItem>(this.originalListModel);
		this.originalList.setDragEnabled(true);
		this.originalList.setLayoutOrientation(JList.VERTICAL);
		this.originalList.setCellRenderer(new ListItemListCellRenderer());

		this.previewList = new JList<ListItem>(this.previewListModel);
		this.previewList.setLayoutOrientation(JList.VERTICAL);
		this.previewList.setCellRenderer(new ListItemListCellRenderer());

		final JScrollPane listScrollerOriginal = new JScrollPane(this.originalList);
		final JScrollPane listScrollerPreview  = new JScrollPane(this.previewList);
		listScrollerOriginal.setPreferredSize(new Dimension(listScrollerOriginal.getPreferredSize().width, listScrollerOriginal
				.getPreferredSize().height));
		listScrollerPreview.setPreferredSize(new Dimension(listScrollerPreview.getPreferredSize().width, listScrollerPreview
				.getPreferredSize().height));

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollerOriginal, listScrollerPreview);
		this.splitPane.setOneTouchExpandable(true);
		this.splitPane.setDividerSize(10);
		this.splitPane.setResizeWeight(.5d);

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = .5;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;

		this.getContentPane().add(this.splitPane, gbc);

		gbc.gridx = 5;
		gbc.gridy = 4;
		gbc.weightx = .5;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.fill = GridBagConstraints.BOTH;

		/*nameItemList = new JTextArea();
		nameItemList.setBorder(BorderFactory.createEmptyBorder());
		nameItemList.setText("Test\nTest2\nTest3");

		this.getContentPane().add(nameItemList, gbc);*/
	}

	/**
	 * Initilize the top menu of the application.
	 */
	private void initMenu() {
		final JMenuBar menuBar      = new JMenuBar();
		final JMenu    settingsMenu = new JMenu("Settings");
		this.miSettingSwitchFileSplit = new JMenuItem("Switch Filesplit");
		this.miSettingSwitchFileSplit.addActionListener(this);

		settingsMenu.add(this.miSettingSwitchFileSplit);

		menuBar.add(settingsMenu);

		this.setJMenuBar(menuBar);
	}

	@Override
	public void insertUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

	/**
	 * Opens a dialog where the user can delete a preset.
	 */
	private void onDeletePreset() {
		final Object[] possibilities = this.presetList.getPresets();
		final Preset preset = (Preset) JOptionPane.showInputDialog(this, "Choose the preset to delete (this cannot be undone)",
																   "Delete preset", JOptionPane.QUESTION_MESSAGE, null, possibilities, null);

		// If a string was returned, say so.
		if ((preset != null)) {
			this.presetList.remove(preset);
			this.presetList.save();
		} else {
			System.out.println("choosed nothing");
		}
		this.updatePresetButton();
	}

	/**
	 * Opens a dialog where the user can overwrite an existing preset.
	 */
	private void onOverwritePreset() {
		final Object[] possibilities = this.presetList.getPresets();
		if (possibilities.length > 0) {
			final Preset preset = (Preset) JOptionPane.showInputDialog(this, "Choose the preset to overwrite (this cannot be undone)",
																	   "Overwrite preset", JOptionPane.QUESTION_MESSAGE, null, possibilities, null);

			// If a string was returned, say so.
			if ((preset != null)) {
				this.presetList.replace(preset, this.createCurrentPreset(preset.getTitle()));
				this.presetList.save(preset.getTitle());
			} else {
				System.out.println("choosed nothing");
			}
		} else {
			System.out.println("nothing to overwrite");
		}
	}

	/**
	 * Renames all files with the current configuration.
	 */
	private void onRename() {
		final Enumeration<ListItem>    items    = this.previewListModel.elements();
		ListItem                       tempItem;
		final EventArrayList<ListItem> tempList = new EventArrayList<ListItem>();
		File                           tempFile;
		while (items.hasMoreElements()) {
			tempItem = items.nextElement();
			tempFile = new File(ReplacerHelper.getFilepath(tempItem.getFile()) + File.separator + tempItem.getTitle());
			if (!tempFile.exists() && tempItem.getFile().renameTo(tempFile)) {
				try {
					tempList.add(new ListItem(tempFile));
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			} else {
				tempList.add(tempItem);
			}
		}
		this.resetCurrentPreset(true);
		this.allList.addAll(tempList);
	}

	/**
	 * Opens a dialog where a user can type the title for the preset.
	 */
	private void onSaveAsNewPreset() {
		final String title = JOptionPane.showInputDialog(this, "Preset title", "Preset title(ti)", JOptionPane.QUESTION_MESSAGE);
		// If a string was returned, say so.
		if ((title != null) && (title.length() > 0)) {
			if (this.presetList.get(title) == null) {
				this.presetList.add(this.createCurrentPreset(title)).save();
			} else {
				final int selectedOpt = JOptionPane
						.showConfirmDialog(this, "Preset with name \"" + title + "\" already exists. Overwrite?");
				if (selectedOpt == 0) {
					this.presetList.replace(this.presetList.get(title), this.createCurrentPreset(title));
					this.presetList.save(title);
				} else if (selectedOpt == 1) {
					this.onSaveAsNewPreset();
				} else {
					System.out.println("do nothing");
				}
			}
		}
		this.updatePresetButton();
	}

	/**
	 * Shows a input dialog where the user can choose the default preset.
	 */
	private void onSetAsDefaultPreset() {
		final Object[] possibilities = RenameWindow.prepend(this.presetList.getTitles(), "");
		final String s = (String) JOptionPane.showInputDialog(this, "Choose the preset to set as default", "Set preset as default",
															  JOptionPane.QUESTION_MESSAGE, null, possibilities, null);

		// If a string was returned, say so.
		if ((s != null)) {
			if (s.length() > 0) {
				PropertiesHelper.getProperties().setProperty("presets.default", s);
			} else {
				PropertiesHelper.getProperties().remove("presets.default");
			}
		} else {
			System.out.println("choosed nothing");
		}
	}

	@Override
	public void removeUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

	/**
	 * Replace with regular expression.
	 *
	 * @param fileNameMask
	 * 		String
	 *
	 * @return new fileNameMask
	 */
	private String replaceDynamicInputs(String fileNameMask) {
		Pattern p;
		Matcher m;
		String  fieldAEndStr;
		String  fieldAStartStr;
		// replace with regexp from dynamic inputs
		for (final DynamicInputCheckTupel tupel : this.dynamicReplaceFields) {
			// reset colors
			tupel.getFieldA().setBackground(UIManager.getColor("TextField.background"));
			tupel.getFieldAStart().setBackground(UIManager.getColor("TextField.background"));
			tupel.getFieldAEnd().setBackground(UIManager.getColor("TextField.background"));
			tupel.getFieldB().setBackground(UIManager.getColor("TextField.background"));

			fieldAEndStr = "";
			fieldAStartStr = "";
			try {

				// look ahead
				if ((tupel.getFieldAEnd() != null) && !tupel.getFieldAEnd().getText().equals("")) {
					if (tupel.getFieldAEndCheck().isSelected()) {
						fieldAEndStr = "(?!" + tupel.getFieldAEnd().getText() + ")";
					} else {
						fieldAEndStr = "(?=" + tupel.getFieldAEnd().getText() + ")";
					}
				}
				// look behind
				if ((tupel.getFieldAStart() != null) && !tupel.getFieldAStart().getText().equals("")) {
					if (tupel.getFieldAStartCheck().isSelected()) {
						fieldAStartStr = "(?<!" + tupel.getFieldAStart().getText() + ")";
					} else {
						fieldAStartStr = "(?<=" + tupel.getFieldAStart().getText() + ")";
					}
				}
				p = Pattern.compile(fieldAStartStr + tupel.getFieldA().getText() + fieldAEndStr);

				m = p.matcher(fileNameMask);
				if (m.find()) {
					if (tupel.getCheckField().isSelected()) {
						fileNameMask = m.replaceAll(tupel.getFieldB().getText());
					} else {
						fileNameMask = m.replaceFirst(tupel.getFieldB().getText());
					}
				}
			} catch (final PatternSyntaxException ex) {
				System.out.println("regex error - invalid syntax");
				if (tupel.getFieldA().getText().equals(ex.getPattern())) {
					tupel.getFieldA().setBackground(Color.RED);
				} else if (fieldAStartStr.equals(ex.getPattern())) {
					tupel.getFieldAStart().setBackground(Color.RED);
				} else if (fieldAEndStr.equals(ex.getPattern())) {
					tupel.getFieldAEnd().setBackground(Color.RED);
				}
			} catch (final IndexOutOfBoundsException ex) {
				System.out.println("regex error - no group found");
				tupel.getFieldB().setBackground(Color.RED);
			}
		}

		return fileNameMask;
	}

	/**
	 * Replaces the title of a listItem and return the item.
	 *
	 * @param fileNameMask
	 * 		String
	 * @param listItem
	 * 		The ListItem
	 * @param originalItem
	 * 		The original ListItem
	 * @param itemPos
	 * 		The item position in list.
	 *
	 * @return ListItem to set
	 */
	private ListItem replaceListItemName(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		fileNameMask = this.replacerController.doReplace(fileNameMask, originalItem.getFile(), itemPos);

		@SuppressWarnings("unused")
		final ConcurrentHashMap<String, String> generatedDynamicValues = new ConcurrentHashMap<String, String>();

		fileNameMask = this.replaceDynamicInputs(fileNameMask);

		listItem.setTitle(fileNameMask);
		return listItem;
	}

	/**
	 * Clears the current preset.
	 *
	 * @param doNotIgnoreList
	 * 		Do not ignore list. List will also be cleared.
	 */
	private void resetCurrentPreset(final boolean doNotIgnoreList) {
		this.fileNameInput.setText("[n]");
		this.dynamicReplaceFields.clear(false);

		if (doNotIgnoreList) {
			this.allList.clear();
		}
	}

	/**
	 * Sets the preset to the gui.
	 *
	 * @param preset
	 * 		The preset to use.
	 */
	private void setValuesFromPreset(final Preset preset) {
		if (preset != null) {
			this.dynamicReplaceFields.clear(true);
			this.fileNameInput.setText(preset.getNameInput());
			for (int i = 0; i < preset.getDynamicInputList().size(); i++) {
				this.dynamicReplaceFields.addLayer(preset.getDynamicInputList().get(i));
			}
		}
	}

	/**
	 * Replaces a preset with the current configuration.
	 */
	private void updatePresetButton() {
		if (this.presetList.size() > 0) {
			this.overwritePreset.setEnabled(true);
			this.deletePreset.setEnabled(true);
			this.setAsDefaultPreset.setEnabled(true);
		} else {
			this.overwritePreset.setEnabled(false);
			this.deletePreset.setEnabled(false);
			if (PropertiesHelper.getProperties().getProperty("presets.default") == null) {
				this.setAsDefaultPreset.setEnabled(false);
			}
		}
	}

	/**
	 * Updates the preview list.
	 */
	private void updatePreviewList() {
		ListItem                    tempItem;
		final Enumeration<ListItem> items = this.previewListModel.elements();
		int                         i     = 0;
		while (items.hasMoreElements()) {
			tempItem = this.replaceListItemName(this.fileNameInput.getText(), items.nextElement(), this.allList.get(i), i);
			this.previewListModel.setElementAt(tempItem, i);
			i++;
		}
	}

	/**
	 * If the user type text to the fileMask input, this method will be called.
	 * If newFileMask is empty or NULL, the full list will be displayed.
	 * Otherwise, the filtered list will be displayed.
	 *
	 * @param newFileMask
	 * 		New file mask.
	 */
	public void onUpdateFileMask(final String newFileMask) {
		if (newFileMask.isEmpty()) {
			this.originalListModel.clear();
			this.previewListModel.clear();

			for (ListItem listItem : allList) {
				this.originalListModel.addElement(listItem);
				this.previewListModel.addElement(listItem);
			}
		} else {
			boolean isValid = Boolean.TRUE;
			try {
				// just checks the pattern to compile.
				//noinspection ResultOfMethodCallIgnored
				Pattern.compile(newFileMask);
			} catch (PatternSyntaxException e) {
				isValid = Boolean.FALSE;
			}

			this.originalListModel.clear();
			this.previewListModel.clear();

			if (isValid) {
				for (ListItem listItem : allList) {
					if (listItem.getFile().getName().matches(newFileMask)) {
						this.originalListModel.addElement(listItem);
						this.previewListModel.addElement(listItem);
					}
				}
			} else {
				System.err.println("\"" + newFileMask + "\" is not a valid regex");
			}
		}
	}

	/**
	 * Updates the fields when file extension filter changes.
	 *
	 * @param newFileExtensionMask
	 * 		New file extension mask.
	 */
	public void onUpdateFilterFileExtension(final String newFileExtensionMask) {
		if (newFileExtensionMask.isEmpty()) {
			this.originalListModel.clear();
			this.previewListModel.clear();

			for (ListItem listItem : allList) {
				this.originalListModel.addElement(listItem);
				this.previewListModel.addElement(listItem);
			}
		} else {
			this.originalListModel.clear();
			this.previewListModel.clear();

			for (ListItem listItem : allList) {
				String fileExtension = FileHelper.getFileExtension(listItem.getFile());
				if (fileExtension != null && fileExtension.equalsIgnoreCase(newFileExtensionMask)) {
					this.originalListModel.addElement(listItem);
					this.previewListModel.addElement(listItem);
				}
			}
		}
	}
}
