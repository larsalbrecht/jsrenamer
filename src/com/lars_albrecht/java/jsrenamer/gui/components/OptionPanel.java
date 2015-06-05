/**
 *
 */
package com.lars_albrecht.java.jsrenamer.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lars_albrecht.java.jsrenamer.gui.RenameWindow;

/**
 * @author lalbrecht
 *
 */
public class OptionPanel extends JPanel implements DocumentListener {

    /**
     *
     */
    private static final long	serialVersionUID			= -6282044655096194511L;

    private RenameWindow		renameWindow				= null;

    private JTextField			fileMaskInput				= null;
    @SuppressWarnings("unused")
    private JCheckBox			ignoreFileExtensionCheckBox	= null;
    private JTextField			filterFileExtension			= null;

    public OptionPanel(RenameWindow renameWindow) {
        this.renameWindow = renameWindow;

        final GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);

        this.init();
    }

    private void init() {
        this.initTitle();

        this.initFileMask();
        this.initFileExt();
    }

    private void initTitle() {
        JLabel titleLabel = new JLabel("Options");
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        this.add(titleLabel, gbc);
    }

    private void initFileMask() {
        JLabel fileMaskInputTitle = new JLabel("File mask:");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(fileMaskInputTitle, gbc);

        this.fileMaskInput = new JTextField();
        this.fileMaskInput.getDocument().addDocumentListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(this.fileMaskInput, gbc);
    }

    private void onFileMaskInputChanged(DocumentEvent e) {
        renameWindow.onUpdateFileMask(this.fileMaskInput.getText());
    }

    private void onFilterFileExtensionInputChanged(DocumentEvent e) {
        renameWindow.onUpdateFilterFileExtension(this.filterFileExtension.getText());
    }

    private void initFileExt() {
        GridBagConstraints gbc = new GridBagConstraints();
        /*
         * JLabel ignoreFileExtensionTitle = new
         * JLabel("Ignore fileextension:"); gbc.gridx = 0; gbc.gridy = 2;
         * gbc.weighty = 0; gbc.gridwidth = 2; gbc.insets = new Insets(10, 10,
         * 0, 10); gbc.anchor = GridBagConstraints.LINE_START;
         * this.add(ignoreFileExtensionTitle, gbc);
         *
         * this.ignoreFileExtensionCheckBox = new JCheckBox(); gbc = new
         * GridBagConstraints(); gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 1;
         * gbc.weighty = 0; gbc.gridwidth = 2; gbc.insets = new Insets(10, 10,
         * 0, 10); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor =
         * GridBagConstraints.LINE_START;
         * this.add(this.ignoreFileExtensionCheckBox, gbc);
         */

        JLabel filterFileExtensionTitle = new JLabel("Filter fileextension:");
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(filterFileExtensionTitle, gbc);

        this.filterFileExtension = new JTextField();
        this.filterFileExtension.getDocument().addDocumentListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(this.filterFileExtension, gbc);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == this.fileMaskInput.getDocument()) {
            this.onFileMaskInputChanged(e);
        } else if (e.getDocument() == this.filterFileExtension.getDocument()) {
            this.onFilterFileExtensionInputChanged(e);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == this.fileMaskInput.getDocument()) {
            this.onFileMaskInputChanged(e);
        } else if (e.getDocument() == this.filterFileExtension.getDocument()) {
            this.onFilterFileExtensionInputChanged(e);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == this.fileMaskInput.getDocument()) {
            this.onFileMaskInputChanged(e);
        } else if (e.getDocument() == this.filterFileExtension.getDocument()) {
            this.onFilterFileExtensionInputChanged(e);
        }
    }
}
