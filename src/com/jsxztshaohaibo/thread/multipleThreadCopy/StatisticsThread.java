package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.text.DecimalFormat;

public class StatisticsThread extends Thread {

	private long fileSize;//�ļ����ܴ�С
	private CopyerThread[] cts ;//��������ļ���
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
	 * ��ȡ�����߳��ѿ��������ݴ�С
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
			//ȡ�������߳̿������ܴ�С
			long copyedTotalSize = getCopyedTotalSize();
			//�����İٷֱ�
			double  schedule = (double)copyedTotalSize / (double)fileSize ;
			
			msg = format.format(schedule);
			
			//System.out.println("�ļ��ѿ�����"+msg);
			
			if(copyedTotalSize == fileSize){
				//break;
				flag =true;
			}
			if(flag){
				System.out.println("����������"+copyedTotalSize);
				break;
			}
		}
		System.out.println("�ļ��ѿ�����"+msg);
		System.out.println("renameto is successful ? "+  tmpFile.renameTo(destFile));
		System.out.println(Thread.currentThread().getName()+"�������.");
	
	}
	
	
 
}
