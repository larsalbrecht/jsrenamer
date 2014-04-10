/**
 * 
 */
package com.lars_albrecht.java.jsrenamer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lars_albrecht.java.jsrenamer.gui.RenameWindow;

/**
 * @author lalbrecht
 * 
 */
public class JSRenamerMain {

	public static void main(final String[] args) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (final ClassNotFoundException e) {
			// handle exception
		} catch (final InstantiationException e) {
			// handle exception
		} catch (final IllegalAccessException e) {
			// handle exception
		}
		new JSRenamerMain();
	}

	public JSRenamerMain() {
		new RenameWindow();
	}
}
