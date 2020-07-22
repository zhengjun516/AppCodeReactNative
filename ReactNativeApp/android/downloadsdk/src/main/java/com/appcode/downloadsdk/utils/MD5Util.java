package com.appcode.downloadsdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5(String origin) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(origin.getBytes("UTF8"));
            byte[] digest = md.digest();
            String result = "";
            for (int i = 0; i < digest.length; i++) {
                result += Integer.toHexString((0x000000FF & digest[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFileMd5(String filePath) {
        String md5 = null;
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            md5 = getFileInputStreamMd5(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


    public static String getFileInputStreamMd5(InputStream inputStream) {
        BigInteger bigInteger = BigInteger.valueOf(0);
        try {
            byte[] buffer = new byte[8192];
            int length = 0;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            while ((length = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            byte[] bytes = messageDigest.digest();
            bigInteger = new BigInteger(1, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String md5 = bigInteger.toString(16);
        if (!md5.equals("0")) {
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
        }
        return md5;
    }
}
