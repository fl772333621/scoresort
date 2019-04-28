/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.utils;

/**
 * @author mengwei
 */
public class ExceptionUtil {

    public static String printStackTraceMgr(StackTraceElement[] stes) {
        StackTraceElement ste;
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : stes) {
            ste = stackTraceElement;
            sb.append("at ");
            sb.append(ste.getClassName());
            sb.append(".");
            sb.append(ste.getMethodName());
            sb.append("(");
            sb.append(ste.getFileName());
            sb.append(":");
            sb.append(ste.getLineNumber());
            sb.append(")\n");
        }
        return sb.toString();
    }

}
