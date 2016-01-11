/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.components.renderer;

import com.lars_albrecht.java.jsrenamer.core.model.ListItem;

import javax.swing.*;
import java.awt.*;

/**
 * @author lalbrecht
 */
public class ListItemListCellRenderer extends JLabel implements ListCellRenderer<ListItem> {

	/**
	 *
	 */
	private static final long serialVersionUID = 5828694820395896822L;

	@Override
	public Component getListCellRendererComponent(final JList<? extends ListItem> list,
												  final ListItem value,
												  final int index,
												  final boolean isSelected,
												  final boolean cellHasFocus) {

		this.setOpaque(true);
		if (isSelected) {
			this.setForeground(UIManager.getColor("List.selectionForeground"));
			this.setBackground(UIManager.getColor("List.selectionBackground"));
		} else {
			if (value.getTitle().equals(value.getFile().getName())) {
				this.setForeground(UIManager.getColor("textInactiveText"));
				this.setBackground(UIManager.getColor("List.background"));
			} else {
				this.setForeground(UIManager.getColor("List.foreground"));
				this.setBackground(UIManager.getColor("List.background"));
			}
		}

		this.setText(value.toString());

		return this;
	}

}
