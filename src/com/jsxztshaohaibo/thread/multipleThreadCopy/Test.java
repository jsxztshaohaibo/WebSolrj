package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {
	
	
	@org.junit.Test
	public void test(){
		 File soucreFile = new File("F:/2/01.avi");
		 File tmpFile =new File("F:/01.avi.tmp");
		 long start =System.currentTimeMillis();
			RandomAccessFile ins=null;
			RandomAccessFile outs = null;
			try {
				ins = new RandomAccessFile(soucreFile,"r");
				outs = new RandomAccessFile(tmpFile,"rw");
				
				byte[] b = new byte[1024*1024];
				int len = ins.read(b);
				while( (len !=-1) ){
					
					outs.write(b, 0, len);
					len = ins.read(b);
					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					outs.close();
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			long end =System.currentTimeMillis();
			System.err.println(Thread.currentThread().getName()+"拷贝完成; 耗时："+(end - start )+" ms.");
	} 
	public static void main (String [] args ){
		File soucreFile = new File("F:/2/12.WebService.zip");
		// 1.我们可以先获取到系统可用的处理器核心数：
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("可用的处理器核心数:"+availableProcessors);
		MainThread.startWork(soucreFile , "F:/", availableProcessors);
	}
}
