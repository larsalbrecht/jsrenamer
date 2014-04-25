/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.ArrayUtils;

import com.lars_albrecht.java.jsrenamer.gui.components.DynamicInputCheckPanel;
import com.lars_albrecht.java.jsrenamer.gui.components.MenuButton;
import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;
import com.lars_albrecht.java.jsrenamer.gui.components.renderer.ListItemListCellRenderer;
import com.lars_albrecht.java.jsrenamer.gui.handler.FileTransferHandler;
import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.model.Preset;
import com.lars_albrecht.java.jsrenamer.model.PresetList;
import com.lars_albrecht.java.jsrenamer.objects.ArrayListEvent;
import com.lars_albrecht.java.jsrenamer.objects.EventArrayList;
import com.lars_albrecht.java.jsrenamer.objects.IArrayListEventListener;

/**
 * @author lalbrecht
 * 
 */
public class RenameWindow extends JFrame implements IArrayListEventListener<ListItem>, DocumentListener, ActionListener {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public static Object[] prepend(final Object[] oldArray, final Object o) {

		final Object[] newArray = (Object[]) Array.newInstance(oldArray.getClass().getComponentType(), oldArray.length + 1);
		System.arraycopy(oldArray, 0, newArray, 1, oldArray.length);
		newArray[0] = o;
		return newArray;
	}

	private PresetList					presetList					= null;
	private JList<ListItem>				originalList				= null;

	private JList<ListItem>				previewList					= null;
	private DefaultListModel<ListItem>	originalListModel			= null;

	private DefaultListModel<ListItem>	previewListModel			= null;

	private EventArrayList<ListItem>	allList						= null;

	private JTextField					fileNameInput				= null;

	private DynamicInputCheckPanel		dynamicReplaceFields		= null;

	private JButton						renameButton				= null;
	private MenuButton					presetButton				= null;
	private JMenuItem					saveAsNewPreset				= null;
	private JMenuItem					overwritePreset				= null;
	private JMenuItem					setAsDefaultPreset			= null;
	private JMenuItem					deletePreset				= null;

	private JMenuItem					resetCurrent				= null;

	private JSplitPane					splitPane					= null;

	// ** MENU ITEMS **//
	private JMenuItem					miSettingSwitchFileSplit	= null;

	public RenameWindow() {
		super("JSRenamer");
		this.presetList = new PresetList("presets", true);
		this.allList = new EventArrayList<ListItem>();
		this.allList.addListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(600, 400);
		this.setLocation(50, 50);

		this._initForms();

		this.setVisible(true);

	}

	private void _initForms() {
		final GridBagLayout gbl = new GridBagLayout();
		this.getContentPane().setLayout(gbl);

		this.initMenu();
		this.initInput();
		this.initDynamicInput();
		this.initLists();
		this.initBottomBar();

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
			this.fileNameInput.setText("");
			this.dynamicReplaceFields.clear();
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
	 * Returns the filepath of a file.
	 * 
	 * @param file
	 * @return filePath
	 */
	private String getFilepath(final File file) {
		return file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
	}

	/**
	 * Return the folder name of the folder with index folderIndex (reverse from
	 * file).
	 * 
	 * @param item
	 * @param folderIndex
	 * @return
	 */
	private String getFolderName(final ListItem item, final int folderIndex) {
		final String path = this.getFilepath(item.getFile());
		final String[] pathsArr = path.split(Pattern.quote(File.separator));
		if (folderIndex < (pathsArr.length - 1)) {
			ArrayUtils.reverse(pathsArr);
			return pathsArr[folderIndex];
		}
		return "";
	}

	private String getPatternForDecimalFormat(final int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += "0";
		}
		return result;
	}

	private void initBottomBar() {
		final GridBagConstraints gbc = new GridBagConstraints();

		this.presetButton = new MenuButton("Preset");
		this.presetButton.setToolTipText("Presets");

		this.saveAsNewPreset = new JMenuItem("Save as new preset");
		this.overwritePreset = new JMenuItem("Overwrite preset");
		this.setAsDefaultPreset = new JMenuItem("Set as default preset");
		this.setAsDefaultPreset.setEnabled(false);
		this.deletePreset = new JMenuItem("Delete preset");
		this.resetCurrent = new JMenuItem("Reset");

		this.saveAsNewPreset.setToolTipText("Create a new preset with the current configuration for later use.");
		this.overwritePreset.setToolTipText("Overwrite an existing preset with the current configuration (cannot be undone).");
		this.setAsDefaultPreset.setToolTipText("Currently disabled - Set a preset as default preset to load on startup.");
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
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.PAGE_END;

		this.getContentPane().add(this.presetButton, gbc);

		this.renameButton = new JButton("Rename");
		this.renameButton.setToolTipText("Renames the files in the list with the current configuration.");
		final Font bFont = this.renameButton.getFont();
		final Font newBFont = new Font(bFont.getName(), Font.BOLD, bFont.getSize() + 1);
		this.renameButton.setFont(newBFont);
		this.renameButton.addActionListener(this);
		gbc.gridx = 2;
		gbc.gridy = 3;
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
		gbc.gridy = 1;
		gbc.weightx = .1;
		gbc.weighty = 0;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;

		this.getContentPane().add(this.dynamicReplaceFields, gbc);
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
		this.fileNameInput = new JTextField("[n]");
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
		final JScrollPane listScrollerPreview = new JScrollPane(this.previewList);
		listScrollerOriginal.setPreferredSize(new Dimension(listScrollerOriginal.getPreferredSize().width, listScrollerOriginal
				.getPreferredSize().height));
		listScrollerPreview.setPreferredSize(new Dimension(listScrollerPreview.getPreferredSize().width, listScrollerPreview
				.getPreferredSize().height));

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollerOriginal, listScrollerPreview);
		this.splitPane.setOneTouchExpandable(true);
		this.splitPane.setDividerSize(10);
		this.splitPane.setResizeWeight(.5d);
		this.getContentPane().add(this.splitPane, gbc);
	}

	private void initMenu() {
		final JMenuBar mb = new JMenuBar();
		final JMenu settingsMenu = new JMenu("Settings");
		this.miSettingSwitchFileSplit = new JMenuItem("Switch Filesplit");
		this.miSettingSwitchFileSplit.addActionListener(this);

		settingsMenu.add(this.miSettingSwitchFileSplit);

		mb.add(settingsMenu);

		this.setJMenuBar(mb);
	}

	@Override
	public void insertUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

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
	}

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
			// TODO disable item if no item is there
			System.out.println("nothing to overwrite");
		}
	}

	private void onRename() {
		final Enumeration<ListItem> items = this.previewListModel.elements();
		ListItem tempItem = null;
		final EventArrayList<ListItem> tempList = new EventArrayList<ListItem>();
		File tempFile = null;
		while (items.hasMoreElements()) {
			tempItem = items.nextElement();
			tempFile = new File(this.getFilepath(tempItem.getFile()) + File.separator + tempItem.getTitle());
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
		this.allList.clear();
		this.fileNameInput.setText("");
		this.dynamicReplaceFields.clear();
		this.allList.addAll(tempList);
	}

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

	}

	private void onSetAsDefaultPreset() {
		final Object[] possibilities = RenameWindow.prepend(this.presetList.getTitles(), "");
		final String s = (String) JOptionPane.showInputDialog(this, "Choose the preset to set as default", "Set preset as default",
				JOptionPane.QUESTION_MESSAGE, null, possibilities, null);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			System.out.println("choosed: " + s);
		} else {
			System.out.println("choosed nothing");
		}
	}

	@Override
	public void removeUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

	/**
	 * Replace the [c], [counter] tag.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @param itemPos
	 * @return new fileNameMask
	 */
	private String replaceCounter(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;
		boolean replaced = false;

		// \[(counter|c)(\W?,\W?(([0-9]+?)\W?,\W?([0-9]+?)\W?,\W?([0-9]+))|\W?,\W?(([0-9]+?)\W?,\W?([0-9]+))|(\W?,\W?([0-9]+)*))*\]
		final String pattern = "\\[(counter|c)(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+?)\\W?,\\W?([0-9]+))|\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))|(\\W?,\\W?([0-9]+)*))*\\]";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);

		int start = 0;
		int step = 1;
		int intWidth = 0;
		DecimalFormat df = null;

		while (m.find()) {
			replaced = false;

			if (m.group(3) != null) { // replace [c, <0-9>, <0-9>, <0-9>]
				start = (m.group(4) != null ? Integer.parseInt(m.group(4)) : start);
				step = (m.group(5) != null ? Integer.parseInt(m.group(5)) : step);
				intWidth = (m.group(6) != null ? Integer.parseInt(m.group(6)) : intWidth);
				df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

				fileNameMask = fileNameMask.replaceFirst(pattern, df.format(((itemPos * step) + start)));
				replaced = true;
			} else if (m.group(7) != null) { // replace [c, <0-9>, <0-9>]
				start = (m.group(8) != null ? Integer.parseInt(m.group(8)) : start);
				step = (m.group(9) != null ? Integer.parseInt(m.group(9)) : step);
				df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

				fileNameMask = fileNameMask.replaceFirst(pattern, df.format(((itemPos * step) + start)));
				replaced = true;
			} else if (m.group(10) != null) { // replace [c, <0-9>]
				start = (m.group(11) != null ? Integer.parseInt(m.group(11)) : start);
				df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

				fileNameMask = fileNameMask.replaceFirst(pattern, df.format(((itemPos * step) + start)));
				replaced = true;
			} else { // replace [c]
				df = new DecimalFormat(this.getPatternForDecimalFormat(intWidth));

				fileNameMask = fileNameMask.replaceFirst(pattern, df.format(((itemPos * step) + start)));
				replaced = true;
			}

			if (!replaced) { // replace unfound
				fileNameMask = fileNameMask.replaceFirst(pattern, "");
			}
		}

		return fileNameMask;
	}

	/**
	 * Replace the [d], [date] tag.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @param itemPos
	 * @return new fileNameMask
	 */
	private String replaceDate(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;

		// \[(date|d)(\W?,\W?(.*?))*\]
		final String pattern = "\\[(date|d)(\\W?,\\W?(.*?))*\\]";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);

		final String origDatePattern = "yyyy-MM-d";
		String datePattern = origDatePattern;
		final SimpleDateFormat sdfmt = new SimpleDateFormat();

		while (m.find()) {
			if (m.group(2) != null) { // replace [d, <pattern>]
				datePattern = m.group(3);
			}

			try {
				sdfmt.applyPattern(datePattern);
			} catch (final Exception e) {
				sdfmt.applyPattern(origDatePattern);
			}
			fileNameMask = sdfmt.format(new Date());
		}

		return fileNameMask;
	}

	/**
	 * Replace with regular expression.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @return new fileNameMask
	 */
	private String replaceDynamicInputs(String fileNameMask, final ListItem listItem, final ListItem originalItem) {
		Pattern p = null;
		Matcher m = null;
		String fieldAEndStr = "";
		String fieldAStartStr = "";
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
	 * Replace the [f], [folder] tag.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @param itemPos
	 * @return new fileNameMask
	 */
	private String replaceFolder(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;
		int folderIndex = 0;
		String folderName = null;
		boolean replaced = false;

		// \[(folder|f)([0-9]*)(\W?,\W?(([0-9]+?)\W?,\W?([0-9]+))*|(([0-9]+)*))*\]
		final String pattern = "\\[(folder|f)([0-9]*)(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))*|(([0-9]+)*))*\\]";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);
		while (m.find()) {
			replaced = false;
			if (m.group(2).equals("")) {
				folderIndex = 0;
			} else {
				folderIndex = Integer.parseInt(m.group(2));
			}

			folderName = this.getFolderName(listItem, folderIndex);

			if (m.group(4) != null) { // replace [f|<0-9>, <0-9>, <0-9>]
				if ((Integer.parseInt(m.group(5)) <= folderName.length())
						&& ((Integer.parseInt(m.group(5)) + Integer.parseInt(m.group(6))) <= folderName.length())
						&& (Integer.parseInt(m.group(5)) < (Integer.parseInt(m.group(5)) + Integer.parseInt(m.group(6))))) {
					fileNameMask = fileNameMask
							.replaceFirst(
									pattern,
									folderName.substring(Integer.parseInt(m.group(5)),
											Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(6))));
					replaced = true;
				}
			} else if (m.group(8) != null) { // replace [f|<0-9>, <0-9>]
				if (Integer.parseInt(m.group(8)) <= folderName.length()) {
					fileNameMask = fileNameMask.replaceFirst(pattern, folderName.substring(Integer.parseInt(m.group(8))));
					replaced = true;
				}
			} else { // replace [f|<0-9>]
				fileNameMask = fileNameMask.replaceFirst(pattern, folderName);
				replaced = true;
			}

			if (!replaced) { // replace unfound
				fileNameMask = fileNameMask.replaceFirst(pattern, "");
			}

		}

		return fileNameMask;
	}

	/**
	 * Replaces the title of a listItem and return the item.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @param itemPos
	 * @return ListItem to set
	 */
	private ListItem replaceListItemName(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		fileNameMask = this.replaceName(fileNameMask, listItem, originalItem, itemPos);
		fileNameMask = this.replaceFolder(fileNameMask, listItem, originalItem, itemPos);
		fileNameMask = this.replaceCounter(fileNameMask, listItem, originalItem, itemPos);
		fileNameMask = this.replaceDate(fileNameMask, listItem, originalItem, itemPos);
		fileNameMask = this.replaceSizes(fileNameMask, listItem, originalItem, itemPos);

		@SuppressWarnings("unused")
		final ConcurrentHashMap<String, String> generatedDynamicValues = new ConcurrentHashMap<String, String>();

		fileNameMask = this.replaceDynamicInputs(fileNameMask, listItem, originalItem);

		listItem.setTitle(fileNameMask);
		return listItem;
	}

	/**
	 * Replace the [n], [name] tag.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @param itemPos
	 * @return new fileNameMask
	 */
	private String replaceName(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;
		String fileName = null;
		boolean replaced = false;

		// \[(name|n)(\W?,\W?(([0-9]+?)\W?,\W?([0-9]+))*|(([0-9]+)*))*\]
		final String pattern = "\\[(name|n)(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))*|(([0-9]+)*))*\\]";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);
		while (m.find()) {
			replaced = false;

			fileName = originalItem.getTitle();

			if (m.group(3) != null) { // replace [n, <0-9>, <0-9>]
				if ((Integer.parseInt(m.group(4)) <= fileName.length())
						&& ((Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))) <= fileName.length())
						&& (Integer.parseInt(m.group(4)) < (Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))))) {
					fileNameMask = fileNameMask.replaceFirst(pattern,
							fileName.substring(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))));
					replaced = true;
				}
			} else if (m.group(7) != null) { // replace [n, <0-9>]
				if (Integer.parseInt(m.group(7)) <= fileName.length()) {
					fileNameMask = fileNameMask.replaceFirst(pattern, fileName.substring(Integer.parseInt(m.group(7))));
					replaced = true;
				}
			} else { // replace [n]
				fileNameMask = fileNameMask.replaceFirst(pattern, fileName);
				replaced = true;
			}

			if (!replaced) { // replace unfound
				fileNameMask = fileNameMask.replaceFirst(pattern, "");
			}

		}

		return fileNameMask;
	}

	/**
	 * Replace the [s], [size] tag.
	 * 
	 * @param fileNameMask
	 * @param listItem
	 * @param originalItem
	 * @return
	 */
	private String replaceSizes(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;

		// \[(size|s)((\W?,\W?([u|l]))*\](.+?)(?=((\[(size|s)((\W?,\W?([u|l]))*\]))|$)))
		final String pattern = "\\[(size|s)((\\W?,\\W?([u|l]))*\\](.+?)(?=((\\[(size|s)((\\W?,\\W?([u|l]))*\\]))|$)))";
		p = Pattern.compile(pattern);
		m = p.matcher(fileNameMask);

		while (m.find()) {
			if (m.group(4) != null) { // replace [s, size]
				if (m.group(4).equals("u")) {
					fileNameMask = fileNameMask.replaceFirst(pattern, m.group(5).toUpperCase());
				} else if (m.group(4).equals("l")) {
					fileNameMask = fileNameMask.replaceFirst(pattern, m.group(5).toLowerCase());
				} else {
					fileNameMask = fileNameMask.replaceFirst(pattern, m.group(5));
				}
			} else if (m.group(5) != null) {
				fileNameMask = fileNameMask.replaceFirst(pattern, m.group(5));
			}
		}

		return fileNameMask;
	}

	/**
	 * Updates the preview list
	 */
	private void updatePreviewList() {
		ListItem tempItem = null;
		final Enumeration<ListItem> items = this.previewListModel.elements();
		int i = 0;
		while (items.hasMoreElements()) {
			tempItem = this.replaceListItemName(this.fileNameInput.getText(), items.nextElement(), this.allList.get(i), i);
			this.previewListModel.setElementAt(tempItem, i);
			i++;
		}
	}
}
