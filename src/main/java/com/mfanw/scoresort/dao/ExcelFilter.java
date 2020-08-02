/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.dao;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Administrator
 */
public final class ExcelFilter extends FileFilter {

    private final String ext;
    private final String des;

    public ExcelFilter(String ext, String des) {
        super();
        this.ext = ext;
        this.des = des;
    }

    @Override
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            return extension != null && extension.equalsIgnoreCase(this.ext);
        }
        return false;
    }

    @Override
    public String getDescription() {
        return this.des;
    }

    private String getExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }
}
