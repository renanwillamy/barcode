package com.sample.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utils {
    public static final String CONFIG = "config";

    public static void saveConfiguration(Context context, AppConfiguration config) {
        try {
            File file = new File(context.getFilesDir(), CONFIG + ".dat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(config);
            out.close();
            fileOutputStream.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static AppConfiguration loadConfiguration(Context context) {
        try {
            File file = new File(context.getFilesDir(), CONFIG + ".dat");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return (AppConfiguration) ois.readObject();
        } catch (Exception ex) {
            Log.d("Utils", ex.toString());
            ex.printStackTrace();
        }
        return new AppConfiguration();
    }


}
