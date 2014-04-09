/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicReplaceTupel;

/**
 * @author lalbrecht
 * 
 */
public class DynamicReplaceTextField extends JPanel implements ActionListener, Iterable<DynamicReplaceTupel> {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 2000655932132606479L;

	private int								elemIndex			= 0;

	private JButton							addButton			= null;

	private String							addButtonText		= null;

	private ArrayList<DynamicReplaceTupel>	fieldList			= null;

	private DocumentListener				documentListener	= null;

	public DynamicReplaceTextField(final String addButtonText, final int createItemsAtInit, final DocumentListener documentListener) {
		super();
		this.fieldList = new ArrayList<DynamicReplaceTupel>();
		this.addButtonText = addButtonText;
		this.documentListener = documentListener;

		final GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		for (int i = 0; i < createItemsAtInit; i++) {
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

	/**
	 * Create a new layer with elements
	 * 
	 * @param index
	 * @return JPanel to add
	 */
	private JPanel createNewLayer(final int index) {
		final GridLayout glButtonPanel = new GridLayout();
		glButtonPanel.setColumns(2);
		glButtonPanel.setHgap(10);

		final GridBagLayout gblPanel = new GridBagLayout();
		final JPanel p = new JPanel(gblPanel, true);
		final JPanel buttonPanel = new JPanel(glButtonPanel, true);

		final GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.gridx = 0;
		gbcPanel.gridwidth = 2;
		gbcPanel.gridy = index;
		gbcPanel.weightx = 1;
		gbcPanel.fill = GridBagConstraints.HORIZONTAL;

		final GridBagConstraints gbcCheck = new GridBagConstraints();
		gbcCheck.gridx = 3;
		gbcCheck.gridwidth = 1;
		gbcCheck.gridy = index;

		final JTextField tempFieldA = new JTextField();
		final JTextField tempFieldB = new JTextField();
		final JCheckBox tempCheckField = new JCheckBox();

		tempFieldA.getDocument().addDocumentListener(this.documentListener);
		tempFieldB.getDocument().addDocumentListener(this.documentListener);

		this.fieldList.add(new DynamicReplaceTupel(tempFieldA, tempFieldB, tempCheckField));

		buttonPanel.add(this.fieldList.get(this.fieldList.size() - 1).getFieldA());
		buttonPanel.add(this.fieldList.get(this.fieldList.size() - 1).getFieldB());
		p.add(buttonPanel, gbcPanel);
		p.add(this.fieldList.get(this.fieldList.size() - 1).getCheckField(), gbcCheck);

		return p;
	}

	/**
	 * Returns the tupel where the doc exists if available.
	 * 
	 * @param doc
	 * @return DynamicReplaceTupel
	 */
	public DynamicReplaceTupel getTupelForDocument(final Document doc) {
		for (final DynamicReplaceTupel tupel : this.fieldList) {
			if ((tupel.getFieldA().getDocument() == doc) || (tupel.getFieldB().getDocument() == doc)) {
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
		for (final DynamicReplaceTupel tupel : this.fieldList) {
			if ((tupel.getFieldA().getDocument() == doc) || (tupel.getFieldB().getDocument() == doc)) {
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
	public Iterator<DynamicReplaceTupel> iterator() {
		return this.fieldList.iterator();
	}

}
