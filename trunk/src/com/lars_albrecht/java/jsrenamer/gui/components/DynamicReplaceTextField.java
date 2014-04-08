/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author lalbrecht
 * 
 */
public class DynamicReplaceTextField extends JPanel {

	private int	elemIndex	= 0;

	public DynamicReplaceTextField(final int createItemsAtInit) {
		super();

		final GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		this.setBackground(Color.WHITE);

		for (int i = 0; i < createItemsAtInit; i++) {
			this.addLayer();
		}

		this.setVisible(true);
	}

	public void addLayer() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = this.elemIndex;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		this.add(this.createNewLayer(this.elemIndex), gbc);
		this.elemIndex++;
	}

	private JPanel createNewLayer(final int index) {
		final GridBagLayout gbl = new GridBagLayout();
		final JPanel p = new JPanel(gbl, true);
		p.setBackground(Color.BLACK);

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .9;
		gbc.weighty = .9;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		p.add(new JTextField("X " + index), gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = .1;
		gbc.weighty = .1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;

		p.add(new JButton("X " + index), gbc);

		return p;
	}

}
