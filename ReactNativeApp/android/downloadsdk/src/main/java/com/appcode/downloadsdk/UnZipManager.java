package com.appcode.downloadsdk;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class UnZipManager {

    private UnZipManager() {}

    private static final String TAG = "UnZipManager";

    public static boolean unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());

                if(ze.isDirectory()){
                    newFile.mkdir();
                    ze = zis.getNextEntry();
                    continue;
                }
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public static boolean unzipAssetsToSDCard(String fileName, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) {
            dir.mkdirs();
        }
       // FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            //fis = new FileInputStream(zipFilePath);
            InputStream inputStream = DownloadSdk.getAppContext().getAssets().open(fileName);
            ZipInputStream zis = new ZipInputStream(inputStream);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName2 = ze.getName();
                File newFile = new File(destDir + File.separator + fileName2);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());

                if(ze.isDirectory()){
                    newFile.mkdir();
                    ze = zis.getNextEntry();
                    continue;
                }
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }
}
