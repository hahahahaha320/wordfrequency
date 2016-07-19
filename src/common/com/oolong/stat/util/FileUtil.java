
package com.oolong.stat.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 文件工具箱
 * 
 * @author
 */
public final class FileUtil {

    /**
     * 安全删除文件，如果目标是路径，则不删除
     * @param targetPath
     * @return
     * @throws IOException
     */
    public static int deleteFileSafe(String targetPath)
            throws IOException {
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            return 0;
        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
        return 1;
    }
    
    public static List<File> sortFileByName(File[] files)	{
		List<File> fileList = new ArrayList<File>();
		for(File file : files)	{
			fileList.add(file);
		}
		Collections.sort(fileList,new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return fileList;
	}
	
	public static void main(String[] args) {
		File dir = new File("F:/statlogs/test_ex_all");
		File[] fs = dir.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(File file : fs)	{
			fileList.add(file);
		}
		Collections.shuffle(fileList);
		File[] files = new File[fileList.size()];
		fileList.toArray(files);
		
		List<File> fileList2 = sortFileByName(files);
		
		for(File file : fileList2)	{
			try {
				System.out.println(file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}