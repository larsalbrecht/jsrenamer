/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

import javax.swing.JPanel;

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

		for (int i = 0; i < createItemsAtInit; i++) {
			this.addLayer();
		}

		this.setVisible(true);
	}

	public void addLayer() {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = this.elemIndex;
		gbc.weighty = .1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;

		this.add(this.createNewLayer(this.elemIndex), gbc);
		this.elemIndex++;
	}

	private JPanel createNewLayer(final int index) {
		final JPanel p = new JPanel();

		p.add(new Label("X " + index));
		return p;
	}

}
