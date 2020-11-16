package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.text.DecimalFormat;

public class StatisticsThread extends Thread {

	private long fileSize;//文件的总大小
	private CopyerThread[] cts ;//开启任务的集合
	private File tmpFile ;
	private File destFile;
	public StatisticsThread(String threadName,long fileSize, CopyerThread[] cts,File tmpFile ,File destFile) {
		super(threadName);
		this.fileSize = fileSize;
		this.cts = cts;
		this.tmpFile =tmpFile;
		this.destFile =destFile;
	}
	/**
	 * 获取所有线程已拷贝的数据大小
	 * @return
	 */
	public long getCopyedTotalSize(){
		long totalSize = 0L;
		for(CopyerThread ct:cts){
			totalSize = totalSize + ct.getCopyedSize();
		}
		return totalSize;
	}
	
	@Override
	public void run() {
		DecimalFormat format = new DecimalFormat("##.#%");
		String msg = null;
		boolean flag = false;
		while(true){
			//取得所有线程拷贝的总大小
			long copyedTotalSize = getCopyedTotalSize();
			//拷贝的百分比
			double  schedule = (double)copyedTotalSize / (double)fileSize ;
			
			msg = format.format(schedule);
			
			//System.out.println("文件已拷贝："+msg);
			
			if(copyedTotalSize == fileSize){
				//break;
				flag =true;
			}
			if(flag){
				System.out.println("拷贝总数："+copyedTotalSize);
				break;
			}
		}
		System.out.println("文件已拷贝："+msg);
		System.out.println("renameto is successful ? "+  tmpFile.renameTo(destFile));
		System.out.println(Thread.currentThread().getName()+"工作完成.");
	
	}
	
	
 
}
