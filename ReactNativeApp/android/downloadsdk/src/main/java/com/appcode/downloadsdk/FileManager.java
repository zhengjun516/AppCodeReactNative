package com.appcode.downloadsdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {

	public static void deleteDir(String dirPath) {
		deleteDir(dirPath, true);
	}

	public static void deleteDir(String dirPath, boolean includeDirSelf) {
		try {
			dirPath = trimPathName(dirPath);
			File dir = new File(dirPath);
			if (!isDirExists(dir)) {
				return;
			}

			for (File child : dir.listFiles()) {
				if (isFileExists(child)) {
					deleteFile(child.getAbsolutePath());
				} else if (isDirExists(child)) {
					deleteDir(child.getAbsolutePath(), true);
				}
			}

			if (includeDirSelf) {
				dir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean deleteFile(String filePath) {
		filePath = trimPathName(filePath);
		File file = new File(filePath);
		if (!isFileExists(file)) {
			return false;
		}
		return file.delete();
	}

	public static String trimPathName(String pathName) {
		if (pathName == null) {
			return null;
		}
		int first = 0;
		for (int i=0; i<pathName.length(); ++i) {
			first = i;
			if (pathName.charAt(i) != File.separatorChar) {
				break;
			}
		}

		int last = pathName.length() - 1;
		for (int i=pathName.length()-1; i>=0; --i) {
			last = i;
			if (pathName.charAt(i) != File.separatorChar) {
				break;
			}
		}

		return pathName.substring(first, last+1);
	}

	public static boolean isFileExists(File file) {
		return file.exists() && file.isFile();
	}

	public static boolean isFileExists(String filePath) {
		filePath = trimPathName(filePath);
		return isFileExists(new File(filePath));
	}
	public static boolean isDirExists(File dir) {
		return dir.exists() && dir.isDirectory();
	}
	

}
