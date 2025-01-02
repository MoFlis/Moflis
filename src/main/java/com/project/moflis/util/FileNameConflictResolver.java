package com.project.moflis.util;

import java.io.File;

public class FileNameConflictResolver {

    public static String checkSameFileName(String fileName, String path) {
        int period = fileName.lastIndexOf(".");// test23.txt --> 6

        String f_name = fileName.substring(0, period);// test23
        String suffix = fileName.substring(period); // .txt

        String saveFilePath = path + "/" + fileName;

        File f = new File(saveFilePath);

        int idx = 1;

        while (f != null && f.exists()) {
            StringBuffer sb = new StringBuffer();
            sb.append(f_name);
            sb.append(idx++);
            sb.append(suffix);

            fileName = sb.toString();// test231.txt

            saveFilePath = path + "/" + fileName;

            f = new File(saveFilePath);
        }
        return fileName;
    }
}
