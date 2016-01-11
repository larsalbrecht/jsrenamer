/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lalbrecht
 */
public class MenuButton extends JButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 8850047737315317298L;

	private JPopupMenu menu = null;

	public MenuButton(final String title) {
		super(title);

		this.menu = new JPopupMenu();
		final JButton thisButton = this;

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MenuButton.this.menu.show(thisButton, 0, thisButton.getBounds().height);
			}
		});
	}

	public void addMenuItem(final JMenuItem menuItem) {
		this.menu.add(menuItem);
	}

	@SuppressWarnings("unused")
	public JPopupMenu getMenu() {
		return this.menu;
	}
}
