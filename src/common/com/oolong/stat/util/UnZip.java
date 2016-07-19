package com.oolong.stat.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不能处理中文文件名
 */
public class UnZip {
	private static final int buffer = 2048;
	
	private static Logger log = LoggerFactory.getLogger(UnZip.class);

	public static boolean unZip(String path) {
		int count = -1;
		int index = -1;

		String savepath = "";
		boolean flag = false;
		
		path = path.replaceAll("\\\\", "/");
		
		savepath = path.substring(0, path.lastIndexOf("/")) + "/"; 
		try {
			BufferedOutputStream bos = null;
			ZipEntry entry = null;
			FileInputStream fis = new FileInputStream(path);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));

			while ((entry = zis.getNextEntry()) != null) {
				byte data[] = new byte[buffer];

				String temp = entry.getName();

				flag = isTxt(temp);
				if (!flag)
					continue;

				index = temp.lastIndexOf("/");
				if (index > -1)
					temp = temp.substring(index + 1);
				temp = savepath + temp;

				File f = new File(temp);
				f.createNewFile();

				FileOutputStream fos = new FileOutputStream(f);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = zis.read(data, 0, buffer)) != -1) {
					bos.write(data, 0, count);
				}

				bos.flush();
				bos.close();
			}

			zis.close();
			return true;

		} catch (Exception e) {
			LogUtil.logError("unZip file:"+path+" error", log, e);
		}
		return false;
	}

	public static boolean isTxt(String filename) {
		boolean flag = false;

		if (filename.endsWith(".txt"))
			flag = true;

		return flag;
	}
	
}
