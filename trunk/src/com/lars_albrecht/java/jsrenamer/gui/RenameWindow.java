/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.ArrayUtils;

import com.lars_albrecht.java.jsrenamer.gui.components.DynamicInputCheckPanel;
import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;
import com.lars_albrecht.java.jsrenamer.gui.components.renderer.ListItemListCellRenderer;
import com.lars_albrecht.java.jsrenamer.gui.handler.FileTransferHandler;
import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.objects.ArrayListEvent;
import com.lars_albrecht.java.jsrenamer.objects.EventArrayList;
import com.lars_albrecht.java.jsrenamer.objects.IArrayListEventListener;

/**
 * @author lalbrecht
 * 
 */
public class RenameWindow extends JFrame implements IArrayListEventListener, DocumentListener, ActionListener {

	/**
	 * 
	 */
	private static final long			serialVersionUID		= 1L;

	private JList<ListItem>				originalList			= null;
	private JList<ListItem>				previewList				= null;

	private DefaultListModel<ListItem>	originalListModel		= null;
	private DefaultListModel<ListItem>	previewListModel		= null;

	private EventArrayList<ListItem>	allList					= null;

	private JTextField					fileNameInput			= null;

	private DynamicInputCheckPanel		dynamicReplaceFields	= null;

	private JButton						renameButton			= null;

	public RenameWindow() {
		super("JSRenamer");
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

		this.initInput();
		this.initDynamicInput();
		this.initLists();
		this.initBottomBar();

		this.setTransferHandler(new FileTransferHandler(this.allList));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.renameButton) {
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
			this.allList.addAll(tempList);
		} else {
			this.updatePreviewList();
		}
	}

	@Override
	public void arrayListAddAll(final ArrayListEvent e) {
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
	public void arrayListChanged(final ArrayListEvent ale) {
		this.updatePreviewList();
	}

	@Override
	public void arrayListCleared(final ArrayListEvent e) {
		this.originalListModel.clear();
		this.previewListModel.clear();

	}

	@Override
	public void arrayListItemAdded(final ArrayListEvent e) {
		try {
			final ArrayList<Object> tempList = e.getItems();
			for (final Object object : tempList) {
				this.originalListModel.addElement((ListItem) object);
				this.previewListModel.addElement((ListItem) ((ListItem) object).clone());
			}
		} catch (final CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void arrayListItemRemoved(final ArrayListEvent e) {
		final ArrayList<Object> tempList = e.getItems();
		for (final Object object : tempList) {
			this.originalListModel.removeElement(object);
			this.previewListModel.removeElement(object);
		}
	}

	@Override
	public void changedUpdate(final DocumentEvent e) {
	}

	private void documentChanged(final DocumentEvent e) {
		if (e.getDocument() == this.fileNameInput.getDocument()) {
			this.updatePreviewList();
		} else if (this.dynamicReplaceFields.hasDocument(e.getDocument())) {
			this.updatePreviewList();
		}
	}

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

	private void initBottomBar() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.PAGE_END;

		this.renameButton = new JButton("Rename");
		this.renameButton.addActionListener(this);

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

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = .4;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;

		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollerOriginal, listScrollerPreview);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerSize(10);
		splitPane.setResizeWeight(.5d);
		this.getContentPane().add(splitPane, gbc);
	}

	@Override
	public void insertUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

	@Override
	public void removeUpdate(final DocumentEvent e) {
		this.documentChanged(e);
	}

	private String replaceDynamicInputs(String fileNameMask, final ListItem listItem, final ListItem originalItem) {
		Pattern p = null;
		Matcher m = null;

		// replace with regexp from dynamic inputs
		for (final DynamicInputCheckTupel tupel : this.dynamicReplaceFields) {
			try {
				p = Pattern.compile(tupel.getFieldA().getText());

				m = p.matcher(fileNameMask);
				if (m.find()) {
					if (tupel.getCheckField().isSelected()) {
						fileNameMask = m.replaceAll(tupel.getFieldB().getText());
					} else {
						fileNameMask = m.replaceFirst(tupel.getFieldB().getText());
					}
				}
			} catch (final PatternSyntaxException ex) {
				System.out.println("regex error");
			}
		}

		return fileNameMask;
	}

	private String replaceFolder(String fileNameMask, final ListItem listItem, final ListItem originalItem) {
		Pattern p = null;
		Matcher m = null;
		int folderIndex = 0;
		String folderName = null;
		boolean replaced = false;

		p = Pattern.compile("\\[f([0-9]*)(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))*|(([0-9]+)*))*\\]");
		m = p.matcher(fileNameMask);
		while (m.find()) {
			replaced = false;
			if (m.group(1).equals("")) {
				folderIndex = 0;
			} else {
				folderIndex = Integer.parseInt(m.group(1));
			}

			folderName = this.getFolderName(listItem, folderIndex);

			if (m.group(3) != null) { // replace [f|<0-9>, <0-9>, <0-9>]
				if ((Integer.parseInt(m.group(4)) <= folderName.length())
						&& ((Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))) <= folderName.length())
						&& (Integer.parseInt(m.group(4)) < (Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))))) {
					fileNameMask = m.replaceFirst(folderName.substring(Integer.parseInt(m.group(4)),
							Integer.parseInt(m.group(4)) + Integer.parseInt(m.group(5))));
					System.out.println("replace 3");
					replaced = true;
				}
			} else if (m.group(7) != null) { // replace [f|<0-9>, <0-9>]
				if (Integer.parseInt(m.group(7)) <= folderName.length()) {
					fileNameMask = m.replaceFirst(folderName.substring(Integer.parseInt(m.group(7))));
					System.out.println("replace 7");
					replaced = true;
				}
			} else { // replace [f|<0-9>]
				fileNameMask = m.replaceAll(folderName);
				System.out.println("replace");
				replaced = true;
			}

			if (!replaced) { // replace unfound
				System.out.println("replace unfound");
				fileNameMask = m.replaceFirst("");
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
		fileNameMask = this.replaceName(fileNameMask, listItem, originalItem);
		fileNameMask = this.replaceFolder(fileNameMask, listItem, originalItem);

		final ConcurrentHashMap<String, String> generatedDynamicValues = new ConcurrentHashMap<String, String>();

		fileNameMask = this.replaceDynamicInputs(fileNameMask, listItem, originalItem);

		listItem.setTitle(fileNameMask);
		return listItem;
	}

	private String replaceName(String fileNameMask, final ListItem listItem, final ListItem originalItem) {
		Pattern p = null;
		Matcher m = null;
		String fileName = null;
		boolean replaced = false;

		p = Pattern.compile("\\[n(\\W?,\\W?(([0-9]+?)\\W?,\\W?([0-9]+))*|(([0-9]+)*))*\\]");
		m = p.matcher(fileNameMask);
		while (m.find()) {
			replaced = false;

			fileName = originalItem.getTitle();

			if (m.group(2) != null) { // replace [n, <0-9>, <0-9>]
				if ((Integer.parseInt(m.group(3)) <= fileName.length())
						&& ((Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(4))) <= fileName.length())
						&& (Integer.parseInt(m.group(3)) < (Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(4))))) {
					fileNameMask = m.replaceFirst(fileName.substring(Integer.parseInt(m.group(3)),
							Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(4))));
					System.out.println("replace 2");
					replaced = true;
				}
			} else if (m.group(6) != null) { // replace [n, <0-9>]
				if (Integer.parseInt(m.group(6)) <= fileName.length()) {
					fileNameMask = m.replaceFirst(fileName.substring(Integer.parseInt(m.group(6))));
					System.out.println("replace 6");
					replaced = true;
				}
			} else { // replace [n|<0-9>]
				fileNameMask = m.replaceAll(fileName);
				System.out.println("replace");
				replaced = true;
			}

			if (!replaced) { // replace unfound
				System.out.println("replace unfound");
				fileNameMask = m.replaceFirst("");
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
