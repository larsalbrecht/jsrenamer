/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import com.lars_albrecht.java.jsrenamer.gui.RenameWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author lalbrecht
 */
public class AdvancedInputField extends JTextPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 7267731342413393826L;

	public AdvancedInputField() {
		this.setBorder(UIManager.getBorder("TextField.border"));
		this.setText("[n]");
		this.setPreferredSize(new Dimension(22, 22));
//        final StyledDocument doc = this.getStyledDocument();
//        final Style regular = doc.addStyle("regular", StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
//        doc.addStyle("icon", regular);
//        final Style iconS = doc.addStyle("iconS", regular);
//        final ImageIcon xIcon = AdvancedInputField.createImageIcon("images/counter.png", "xIcon");
//        final JButton button = new JButton();
//        button.setPreferredSize(new Dimension(18, 14));
//        button.setFocusPainted(false);
//        button.setContentAreaFilled(false);
//        button.setBounds(0, 0, 18, 14);
//        button.setBorderPainted(false);
//        if ((xIcon != null)) {
//            button.setIcon(xIcon);
//        } else {
//            button.setText("NOICON");
//        }
//        button.setCursor(Cursor.getDefaultCursor());
//        button.setMargin(new Insets(0, 0, 0, 0));
//        button.setActionCommand("JButton");
		// button.addActionListener(this);
//        StyleConstants.setComponent(iconS, button);

		// try {
		// doc.insertString(doc.getLength(), "test", doc.getStyle("iconS"));
		// } catch (final BadLocationException e) {
		// e.printStackTrace();
		// }

	}

	@SuppressWarnings("unused")
	protected static ImageIcon createImageIcon(final String path, final String description) {
		final java.net.URL imgURL = RenameWindow.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
