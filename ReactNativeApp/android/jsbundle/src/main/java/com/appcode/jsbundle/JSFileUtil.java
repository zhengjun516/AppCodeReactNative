package com.appcode.jsbundle;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JSFileUtil {

	public static void copyAssets(String assetDir, String dir) {
		String[] files;
		try {
			// 获得Assets一共有几多文件
			files = JSBundleSdk.getApplication().getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return;
		}
		File mWorkingPath = new File(dir);
		// 如果文件路径不存在
		if (!mWorkingPath.exists()) {
			// 创建文件夹
			if (!mWorkingPath.mkdirs()) {
				// 文件夹创建不成功时调用
			}
		}

		for (int i = 0; i < files.length; i++) {
			try {
				// 获得每个文件的名字
				String fileName = files[i];
				// 根据路径判断是文件夹还是文件
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						copyAssets(fileName, dir + fileName + "/");
					} else {
						copyAssets(assetDir + "/" + fileName, dir + "/"
								+ fileName + "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (!outFile.exists()) {
					outFile.createNewFile();
				}
				InputStream in = null;
				if (0 != assetDir.length()) {
					in = JSBundleSdk.getApplication().getResources().getAssets().open(assetDir + "/" + fileName);
				}else {
					in = JSBundleSdk.getApplication().getResources().getAssets().open(fileName);
				}
				OutputStream out = new FileOutputStream(outFile);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 *  从assets目录中复制整个文件夹内容
	 *  @param  context  Context 使用CopyFiles类的Activity
	 *  @param  oldPath  String  原文件路径  如：/aa
	 *  @param  newPath  String  复制后路径  如：xx:/bb/cc
	 */
	public static void copyFilesFassets(Context context, String oldPath, String newPath) {
		try {
			//获取assets目录下的所有文件及目录名
			String fileNames[] = context.getAssets().list(oldPath);
			//如果是目录
			if (fileNames.length > 0) {
				File file = new File(newPath);
				//如果文件夹不存在，则递归
				file.mkdirs();
				for (String fileName : fileNames) {
					copyFilesFassets(context,oldPath + "/" + fileName,newPath+"/"+fileName);
				}
			} else {//如果是文件
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
				int byteCount=0;
				//循环从输入流读取 buffer字节
				while((byteCount=is.read(buffer))!=-1) {
					//将读取的输入流写入到输出流
					fos.write(buffer, 0, byteCount);
				}
				fos.flush();//刷新缓冲区
				is.close();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
