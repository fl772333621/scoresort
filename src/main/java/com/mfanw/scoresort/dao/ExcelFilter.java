/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.dao;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Administrator
 */
public final class ExcelFilter extends FileFilter {

    private String ext;
    private String des;

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
            if (extension != null && extension.equalsIgnoreCase(this.ext)) {
                return true;
            }
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
