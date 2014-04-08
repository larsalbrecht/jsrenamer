/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lars_albrecht.java.jsrenamer.gui.components.DynamicReplaceTextField;
import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.objects.ArrayListEvent;
import com.lars_albrecht.java.jsrenamer.objects.EventArrayList;
import com.lars_albrecht.java.jsrenamer.objects.IArrayListEventListener;

/**
 * @author lalbrecht
 * 
 */
public class RenameWindow extends JFrame implements IArrayListEventListener, DocumentListener {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;

	private JList<ListItem>				originalList		= null;
	private JList<ListItem>				previewList			= null;

	private DefaultListModel<ListItem>	originalListModel	= null;
	private DefaultListModel<ListItem>	previewListModel	= null;

	private EventArrayList<ListItem>	allList				= null;

	private JTextField					fileNameInput		= null;

	public RenameWindow() {
		super("JSRenamer");
		this.allList = new EventArrayList<ListItem>();
		this.allList.addListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(600, 400);
		this.setLocation(50, 50);

		this.initForms();

		this.setVisible(true);
	}

	@Override
	public void arrayListChanged(final ArrayListEvent ale) {
	}

	@Override
	public void arrayListCleared(final ArrayListEvent e) {
		this.originalListModel.clear();
		this.previewListModel.clear();
	}

	@Override
	public void arrayListItemAdded(final ArrayListEvent e) {
		try {
			this.originalListModel.addElement((ListItem) e.getItem());
			this.previewListModel.addElement((ListItem) ((ListItem) e.getItem()).clone());
		} catch (final CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void arrayListItemRemoved(final ArrayListEvent e) {
		this.originalListModel.removeElement(e.getItem());
		this.previewListModel.removeElement(e.getItem());
	}

	@Override
	public void changedUpdate(final DocumentEvent e) {
	}

	private void documentChanged(final DocumentEvent e) {
		if (e.getDocument() == this.fileNameInput.getDocument()) {
			ListItem tempItem = null;
			final Enumeration<ListItem> items = this.previewListModel.elements();
			int i = 0;
			while (items.hasMoreElements()) {
				tempItem = this.replaceName(this.fileNameInput.getText(), items.nextElement(), this.allList.get(i), i);
				this.previewListModel.setElementAt(tempItem, i);
				i++;
			}

		}
	}

	private void initDynamicInput() {
		final DynamicReplaceTextField drtf = new DynamicReplaceTextField(5);
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = .1;
		gbc.weighty = .1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.fileNameInput = new JTextField("[n]");
		this.fileNameInput.getDocument().addDocumentListener(this);

		this.getContentPane().add(drtf, gbc);
	}

	private void initForms() {
		final GridBagLayout gbl = new GridBagLayout();
		this.getContentPane().setLayout(gbl);

		this.initInput();

		this.initDynamicInput();

		this.initLists();

	}

	private void initInput() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .1;
		gbc.weighty = .1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.fileNameInput = new JTextField("[n]");
		this.fileNameInput.getDocument().addDocumentListener(this);

		this.getContentPane().add(this.fileNameInput, gbc);
	}

	private void initLists() {
		this.originalListModel = new DefaultListModel<ListItem>();
		this.previewListModel = new DefaultListModel<ListItem>();

		this.originalList = new JList<ListItem>(this.originalListModel);
		this.originalList.setDragEnabled(true);
		this.originalList.setDropMode(DropMode.INSERT);
		this.originalList.setTransferHandler(new FileTransferHandler(this.allList));

		this.originalList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		final JScrollPane listScroller = new JScrollPane(this.originalList);

		this.previewList = new JList<ListItem>(this.previewListModel);
		this.previewList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		final JScrollPane list2Scroller = new JScrollPane(this.previewList);

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = .4;
		gbc.weighty = .9;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;

		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroller, list2Scroller);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(.5);
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

	private ListItem replaceName(String fileNameMask, final ListItem listItem, final ListItem originalItem, final int itemPos) {
		Pattern p = null;
		Matcher m = null;

		// replace [n] with filename
		fileNameMask = fileNameMask.replaceAll("\\[n\\]", originalItem.getTitle());

		// replace [n, X] with substring
		p = Pattern.compile("\\[n,\\W?([0-9]+)\\]");
		m = p.matcher(fileNameMask);

		int x = 0;
		while (m.find()) {
			System.out.println(x + " - " + fileNameMask);
			if (Integer.parseInt(m.group(1)) <= originalItem.getTitle().length()) {
				fileNameMask = fileNameMask.replaceFirst("\\[n,\\W?([0-9]+)\\]",
						originalItem.getTitle().substring(Integer.parseInt(m.group(1))));
			} else {
				fileNameMask = fileNameMask.replaceFirst("\\[n,\\W?([0-9]+)\\]", "");
			}
			x++;
		}

		// replace [n, X, X] with substring
		p = Pattern.compile("\\[n,\\W?([0-9]+),\\W?([0-9]+)\\]");
		m = p.matcher(fileNameMask);

		while (m.find()) {
			if ((Integer.parseInt(m.group(1)) <= originalItem.getTitle().length())
					&& ((Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(2))) <= originalItem.getTitle().length())
					&& (Integer.parseInt(m.group(1)) < (Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(2))))) {
				fileNameMask = fileNameMask.replaceFirst(
						"\\[n,\\W?([0-9]+),\\W?([0-9]+)\\]",
						originalItem.getTitle().substring(Integer.parseInt(m.group(1)),
								Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(2))));
			} else {
				fileNameMask = fileNameMask.replaceFirst("\\[n,\\W?([0-9]+),\\W?([0-9]+)\\]", "");
			}
		}

		listItem.setTitle(fileNameMask);
		return listItem;
	}
}