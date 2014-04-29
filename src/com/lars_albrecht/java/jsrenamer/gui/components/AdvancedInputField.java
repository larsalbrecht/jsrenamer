/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.lars_albrecht.java.jsrenamer.gui.RenameWindow;

/**
 * @author lalbrecht
 * 
 */
public class AdvancedInputField extends JTextPane {

	protected static ImageIcon createImageIcon(final String path, final String description) {
		final java.net.URL imgURL = RenameWindow.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public AdvancedInputField() {
		this.setBorder(UIManager.getBorder("TextField.border"));
		this.setText("[n]");
		this.setPreferredSize(new Dimension(22, 22));
		final StyledDocument doc = this.getStyledDocument();
		final Style regular = doc.addStyle("regular", StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
		doc.addStyle("icon", regular);
		final Style iconS = doc.addStyle("iconS", regular);
		final ImageIcon xIcon = AdvancedInputField.createImageIcon("images/counter.png", "xIcon");
		final JButton button = new JButton();
		button.setPreferredSize(new Dimension(18, 14));
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBounds(0, 0, 18, 14);
		button.setBorderPainted(false);
		if ((xIcon != null)) {
			button.setIcon(xIcon);
		} else {
			button.setText("NOICON");
		}
		button.setCursor(Cursor.getDefaultCursor());
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setActionCommand("JButton");
		// button.addActionListener(this);
		StyleConstants.setComponent(iconS, button);

		// try {
		// doc.insertString(doc.getLength(), "test", doc.getStyle("iconS"));
		// } catch (final BadLocationException e) {
		// e.printStackTrace();
		// }

	}

}
