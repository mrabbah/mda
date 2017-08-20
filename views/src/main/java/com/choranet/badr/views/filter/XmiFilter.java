/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.views.filter;

import java.io.File;

/**
 * XMI Filter for file chooser
 * @author rabbah
 */
public class XmiFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".xmi" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".xmi");
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "XMI documents (*.xmi)";
    }
}
