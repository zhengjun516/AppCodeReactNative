package com.appcode.downloadsdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

	public static boolean isFileExists(File file) {
		return file.exists() && file.isFile();
	}

	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		return file.exists() && file.isFile();
	}

	public static boolean isDirExists(File dir) {
		return dir.exists() && dir.isDirectory();
	}

	public static boolean isDirExists(String dirPath) {
		File file = new File(dirPath);
		return file.exists() && file.isDirectory();
	}

	public static InputStream getFileInputStream(String filePath) throws IOException, FileNotFoundException {
		filePath = filePath.trim();
		InputStream inputStream = new FileInputStream(new File(filePath));
		return inputStream;
	}
}
