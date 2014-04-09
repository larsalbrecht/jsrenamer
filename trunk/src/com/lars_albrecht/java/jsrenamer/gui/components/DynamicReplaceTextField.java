/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lars_albrecht.java.jsrenamer.gui.components.model.DynamicReplaceTupel;

/**
 * @author lalbrecht
 * 
 */
public class DynamicReplaceTextField extends JPanel implements ActionListener, Enumeration<DynamicReplaceTupel> {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 2000655932132606479L;

	private int								elemIndex			= 0;

	private JButton							addButton			= null;

	private String							addButtonText		= null;

	private int								enumIndex			= 0;

	private ArrayList<DynamicReplaceTupel>	fieldList			= null;

	public DynamicReplaceTextField(final String addButtonText, final int createItemsAtInit) {
		super();
		this.fieldList = new ArrayList<DynamicReplaceTupel>();
		this.addButtonText = addButtonText;

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

	private JPanel createNewLayer(final int index) {
		final GridBagLayout gbl = new GridBagLayout();
		final JPanel p = new JPanel(gbl, true);

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .5;
		gbc.weighty = .9;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		final JTextField tempPatternField = new JTextField();
		final JTextField tempReplaceField = new JTextField();
		this.fieldList.add(new DynamicReplaceTupel(tempPatternField, tempReplaceField));
		p.add(this.fieldList.get(this.fieldList.size() - 1).getPatternField(), gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = .5;
		gbc.weighty = .9;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		p.add(this.fieldList.get(this.fieldList.size() - 1).getReplaceField(), gbc);

		return p;
	}

	@Override
	public boolean hasMoreElements() {
		if (this.enumIndex <= (this.fieldList.size() - 1)) {
			return true;
		}
		return false;
	}

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
	public DynamicReplaceTupel nextElement() {
		this.enumIndex++;
		return this.fieldList.get(this.enumIndex - 1);
	}

}
