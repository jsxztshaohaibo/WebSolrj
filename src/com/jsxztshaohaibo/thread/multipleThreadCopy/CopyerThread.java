package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyerThread implements Runnable {
	private File soucreFile; 
	private File tmpFile; 
	private long startPosition;
	private long endPosition;
	
	private long copyedPosition;//已经拷贝的位置
	
	
	

	/**
	 * 
	 * @param soucreFile  源文件
	 * @param tmpFile   目标文件
	 * @param startPosition 开始位置
	 * @param endPosition   结束位置
	 */
	public CopyerThread(File _soucreFile, File _tmpFile, long _startPosition,long _endPosition) {
		this.soucreFile=_soucreFile;
		this.tmpFile=_tmpFile;
		this.startPosition=_startPosition;
		this.endPosition=_endPosition;
		
		this.copyedPosition = _startPosition;//设置已经拷贝到的位置，默认等于起始位置*******
	}
	/**
	 * 获取已经拷贝的大小
	 * @return
	 */
	public long getCopyedSize() {
		return copyedPosition -startPosition ;
	}
	@Override
	public void run() {
		long start =System.currentTimeMillis();
		RandomAccessFile ins=null;
		RandomAccessFile outs = null;
		try {
			ins = new RandomAccessFile(soucreFile,"r");
			outs = new RandomAccessFile(tmpFile,"rw");
			//设置开始读取的位置
			ins.seek(startPosition);
			//设置开始写入的位置
			outs.seek(startPosition);
			
			byte[] b = new byte[1024*1024];
			int len =-1;
			
			while( (copyedPosition < endPosition) &&  ( (len=ins.read(b)) !=-1 )  ){//有数据读取到，并且本任务的大小还没读完
				if((copyedPosition + len) >=endPosition){
					len =(int )(endPosition - copyedPosition);
				} 
				outs.write(b, 0, len);
				copyedPosition +=len;//已写入大小
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
		System.err.println(Thread.currentThread().getName()+"拷贝完成,开始位置:"+startPosition+"，结束位置:"+endPosition+"，拷贝大小："+getCopyedSize() +" ; 耗时："+(end - start )+" ms.");	
	}

}
