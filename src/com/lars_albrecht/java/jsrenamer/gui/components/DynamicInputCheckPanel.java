/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicInputCheckTupel;

/**
 * @author lalbrecht
 * 
 */
public class DynamicInputCheckPanel extends JPanel implements ActionListener, Iterable<DynamicInputCheckTupel> {

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 2000655932132606479L;

	private int									elemIndex			= 0;

	private JButton								addButton			= null;

	private String								addButtonText		= null;

	private ArrayList<DynamicInputCheckTupel>	fieldList			= null;

	private DocumentListener					documentListener	= null;
	private ActionListener						actionListener		= null;

	private int									createItemsAtInit	= 0;

	/**
	 * 
	 * @param addButtonText
	 * @param createItemsAtInit
	 * @param documentListener
	 * @param actionListener
	 */
	public DynamicInputCheckPanel(final String addButtonText, final int createItemsAtInit, final DocumentListener documentListener,
			final ActionListener actionListener) {
		super();
		this.fieldList = new ArrayList<DynamicInputCheckTupel>();
		this.addButtonText = addButtonText;
		this.createItemsAtInit = createItemsAtInit;
		this.documentListener = documentListener;
		this.actionListener = actionListener;

		this._init();
	}

	private void _init() {
		final GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = this.elemIndex + 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		this.removeAll();
		for (int i = 0; i < this.createItemsAtInit; i++) {
			this.addLayer();
		}

		this.initAddButton();

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.addButton) {
			this.addLayer();
		}
	}

	/**
	 * Adds a layer (panel) to this panel.
	 */
	public void addLayer() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = this.elemIndex + 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		this.add(this.createNewLayer(this.elemIndex), gbc);
		this.revalidate();
		this.elemIndex++;
	}

	public void clear() {
		this.fieldList = new ArrayList<DynamicInputCheckTupel>();
		this._init();
	}

	/**
	 * Create a new layer with elements
	 * 
	 * @param index
	 * @return JPanel to add
	 */
	private JPanel createNewLayer(final int index) {
		final GridBagLayout gblPanelLayout = new GridBagLayout();

		final JPanel p = new JPanel(gblPanelLayout, true);

		final GridBagConstraints gbc = new GridBagConstraints();

		final JTextField searchField = new JTextField();
		final JTextField findStartsWithField = new JTextField();
		final JCheckBox findStartsNotCheck = new JCheckBox();
		final JTextField findEndsWithField = new JTextField();
		final JCheckBox findEndsNotCheck = new JCheckBox();
		final JTextField replaceWithField = new JTextField();
		final JCheckBox replaceAllCheck = new JCheckBox();

		searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, searchField.getPreferredSize().height));
		replaceWithField.setPreferredSize(new Dimension(replaceWithField.getPreferredSize().width,
				replaceWithField.getPreferredSize().height));
		findStartsWithField.setPreferredSize(new Dimension(findStartsWithField.getPreferredSize().width, findStartsWithField
				.getPreferredSize().height));
		findEndsWithField.setPreferredSize(new Dimension(findEndsWithField.getPreferredSize().width,
				findEndsWithField.getPreferredSize().height));

		searchField.getDocument().addDocumentListener(this.documentListener);
		findStartsWithField.getDocument().addDocumentListener(this.documentListener);
		findStartsNotCheck.addActionListener(this.actionListener);
		findEndsWithField.getDocument().addDocumentListener(this.documentListener);
		findEndsNotCheck.addActionListener(this.actionListener);
		replaceWithField.getDocument().addDocumentListener(this.documentListener);
		replaceAllCheck.addActionListener(this.actionListener);

		final JLabel searchLabel = new JLabel("Search", SwingConstants.LEFT);
		final JLabel findStartsWithLabel = new JLabel("Before", SwingConstants.LEFT);
		final JLabel findStartsNotLabel = new JLabel("Not", SwingConstants.LEFT);
		final JLabel findEndsWithLabel = new JLabel("Until", SwingConstants.LEFT);
		final JLabel findEndsNotLabel = new JLabel("Not", SwingConstants.LEFT);
		final JLabel replaceWithLabel = new JLabel("Replace with", SwingConstants.LEFT);
		final JLabel replaceAllLabel = new JLabel("Replace all", SwingConstants.LEFT);
		searchLabel.setPreferredSize(new Dimension(searchLabel.getPreferredSize().width, searchLabel.getPreferredSize().height));
		findStartsWithLabel.setPreferredSize(new Dimension(findStartsWithLabel.getPreferredSize().width, findStartsWithLabel
				.getPreferredSize().height));
		findEndsWithLabel.setPreferredSize(new Dimension(findEndsWithLabel.getPreferredSize().width,
				findEndsWithLabel.getPreferredSize().height));
		findEndsNotLabel.setPreferredSize(new Dimension(findEndsNotLabel.getPreferredSize().width,
				findEndsNotLabel.getPreferredSize().height));
		replaceWithLabel.setPreferredSize(new Dimension(replaceWithLabel.getPreferredSize().width,
				replaceWithLabel.getPreferredSize().height));
		replaceAllLabel
				.setPreferredSize(new Dimension(replaceAllLabel.getPreferredSize().width, replaceAllLabel.getPreferredSize().height));

		this.fieldList.add(new DynamicInputCheckTupel(searchField, findStartsWithField, findStartsNotCheck, findEndsWithField,
				findEndsNotCheck, replaceWithField, replaceAllCheck));

		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.weightx = .2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(findStartsWithLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldAStart(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		p.add(findStartsNotLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldAStartCheck(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 10);
		p.add(searchLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldA(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 6;
		gbc.gridwidth = 2;
		gbc.weightx = .2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(findEndsWithLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldAEnd(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		p.add(findEndsNotLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldAEndCheck(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 9;
		gbc.gridwidth = 2;
		gbc.weightx = .2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(replaceWithLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getFieldB(), gbc);

		gbc.gridy = 0;
		gbc.gridx = 11;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		p.add(replaceAllLabel, gbc);
		gbc.gridy = 1;
		p.add(this.fieldList.get(this.fieldList.size() - 1).getCheckField(), gbc);

		return p;
	}

	/**
	 * Returns the tupel where the doc exists if available.
	 * 
	 * @param doc
	 * @return DynamicReplaceTupel
	 */
	public DynamicInputCheckTupel getTupelForDocument(final Document doc) {
		for (final DynamicInputCheckTupel tupel : this.fieldList) {
			if ((tupel.getFieldA().getDocument() == doc) || (tupel.getFieldB().getDocument() == doc)
					|| (tupel.getFieldAEnd().getDocument() == doc) || (tupel.getFieldAStart().getDocument() == doc)) {
				return tupel;
			}
		}
		return null;
	}

	/**
	 * Returns true if the document exists, otherwise false.
	 * 
	 * @param doc
	 * @return hasDocument in list
	 */
	public boolean hasDocument(final Document doc) {
		for (final DynamicInputCheckTupel tupel : this.fieldList) {
			if ((tupel.getFieldA().getDocument() == doc)
					|| ((tupel.getFieldB().getDocument() == doc) || (tupel.getFieldAEnd().getDocument() == doc) || (tupel.getFieldAStart()
							.getDocument() == doc))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the add button to add an additional line.
	 */
	private void initAddButton() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = .1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;

		this.addButton = new JButton(this.addButtonText);
		this.addButton.addActionListener(this);

		this.add(this.addButton, gbc);
	}

	@Override
	public Iterator<DynamicInputCheckTupel> iterator() {
		return this.fieldList.iterator();
	}

}
