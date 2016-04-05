package com.hq.test1.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nami on 4/5/16.
 */
public class FileUtil {
    public FileUtil() {
    }

    public File createFile(String name) throws IOException {
        File file = new File(checkFolder(), name);
        file.createNewFile();
        return file;
    }

    public boolean checkFile(String name) {
        File file = new File(checkFolder(), name);
        return file.exists();
    }

    private String checkFolder() {
        File testFile = new File(Environment.getExternalStorageDirectory(), Constants.APP_FOLDER_NAME);
        try {
            if (!testFile.exists()) {
                testFile.mkdirs();
                testFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testFile.getAbsolutePath();
    }

    public String getFilePath(String name) {
        File docFile = new File(checkFolder(), name);
        return docFile.getAbsolutePath();
    }

    public void removeFile(String name) {
        File file = new File(checkFolder(), name);
        if (file.exists())
            file.delete();
    }

    public void saveHTML(String htmlString, String name) {
        if (checkFile(name))
            removeFile(name);

        File file;
        try {
            file = createFile(name);
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = htmlString.getBytes();
            out.write(data);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
