/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.lars_albrecht.java.jsrenamer.model.ListItem;
import com.lars_albrecht.java.jsrenamer.objects.EventArrayList;

/**
 * @author lalbrecht
 * 
 */
public class FileTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -988966209784678879L;
	private EventArrayList<ListItem>	list				= null;

	public FileTransferHandler(final EventArrayList<ListItem> list) {
		this.list = list;
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
		return TransferHandler.COPY_OR_MOVE;
	}

	@Override
	public boolean importData(final TransferSupport support) {
		try {
			@SuppressWarnings("unchecked")
			final List<File> data = ((List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
			ListItem tempItem = null;
			for (final File item : data) {
				if ((item != null) && item.canRead() && item.canWrite()) {
					tempItem = this.createListItem(item);
					if ((tempItem != null) && !this.list.contains(tempItem)) {
						this.list.add(tempItem);
					} else {
						if (tempItem == null) {
							System.out.println("Item is null");
						} else {
							System.out.println(tempItem + " already in list");
						}
					}
				}
			}
		} catch (final UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return super.importData(support);
	}
}
