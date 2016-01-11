/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.handler;

import com.lars_albrecht.java.jsrenamer.core.model.ListItem;
import com.lars_albrecht.java.jsrenamer.core.objects.EventArrayList;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lalbrecht
 */
public class FileTransferHandler extends TransferHandler {

	/**
	 *
	 */
	private static final long                     serialVersionUID = -988966209784678879L;
	private              EventArrayList<ListItem> list             = null;

	public FileTransferHandler(final EventArrayList<ListItem> list) {
		this.list = list;
	}

	/**
	 * @param f
	 * 		file
	 *
	 * @return count of added data
	 */
	private int addDataToList(final File f) {
		int count = 0;
		if (f.exists() && f.canRead() && f.canWrite()) {
			if (f.isFile()) {
				final ListItem tempItem = this.createListItem(f);
				if ((tempItem != null) && !this.list.contains(tempItem)) {
					this.list.add(tempItem);
					count++;
				} else {
					if (tempItem == null) {
						System.out.println("Item is null");
					} else {
						System.out.println(tempItem + " already in list");
					}
				}
			} else if (f.isDirectory() && f.canExecute()) {
				final File[] files = f.listFiles();
				assert files != null;
				for (final File file : files) {
					count += this.addDataToList(file);
				}
			}
		}
		return count;
	}

	@Override
	public boolean canImport(final TransferSupport support) {
		return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	private ListItem createListItem(final File file) {
		try {
			return new ListItem(file);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getSourceActions(final JComponent c) {
		return TransferHandler.LINK;
	}

	@Override
	public boolean importData(final TransferSupport support) {
		try {
			@SuppressWarnings("unchecked")
			final List<File> data = ((List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
			int count = 0;
			for (final File file : data) {
				count += this.addDataToList(file);
			}
			System.out.println("Added " + count + " item(s)");
		} catch (final UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return super.importData(support);
	}

}
