/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author mengwei
 */
public class ConfigUtil {

    private static Map<String, String> configs = null;

    public static void reloadConfigs() {
        try {
            configs = new HashMap<>();
            File file = new File("score_sort.cfg");
            if (!file.exists()) {
                return;
            }
            String content = FileUtils.readFileToString(file, "UTF-8");
            if (content != null && !content.isEmpty()) {
                for (String line : content.split("\r\n")) {
                    if (line == null || line.isEmpty()) {
                        continue;
                    }
                    String[] kv = line.split("=");
                    configs.put(kv[0], kv[1]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getConfig(String key) {
        if (configs == null) {
            reloadConfigs();
        }
        return configs.get(key);
    }

    public static void saveConfig(String key, String value) {
        if (configs == null) {
            reloadConfigs();
        }
        configs.put(key, value);
        saveConfigsInner();
        reloadConfigs();
    }

    private static void saveConfigsInner() {
        StringBuilder sb = new StringBuilder();
        configs.entrySet().forEach((entry) -> {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\r\n");
        });
        try {
            FileUtils.writeStringToFile(new File("score_sort.cfg"), sb.toString(), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(ConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
