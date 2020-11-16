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
	
	private long copyedPosition;//�Ѿ�������λ��
	
	
	

	/**
	 * 
	 * @param soucreFile  Դ�ļ�
	 * @param tmpFile   Ŀ���ļ�
	 * @param startPosition ��ʼλ��
	 * @param endPosition   ����λ��
	 */
	public CopyerThread(File _soucreFile, File _tmpFile, long _startPosition,long _endPosition) {
		this.soucreFile=_soucreFile;
		this.tmpFile=_tmpFile;
		this.startPosition=_startPosition;
		this.endPosition=_endPosition;
		
		this.copyedPosition = _startPosition;//�����Ѿ���������λ�ã�Ĭ�ϵ�����ʼλ��*******
	}
	/**
	 * ��ȡ�Ѿ������Ĵ�С
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
			//���ÿ�ʼ��ȡ��λ��
			ins.seek(startPosition);
			//���ÿ�ʼд���λ��
			outs.seek(startPosition);
			
			byte[] b = new byte[1024*1024];
			int len =-1;
			
			while( (copyedPosition < endPosition) &&  ( (len=ins.read(b)) !=-1 )  ){//�����ݶ�ȡ�������ұ�����Ĵ�С��û����
				if((copyedPosition + len) >=endPosition){
					len =(int )(endPosition - copyedPosition);
				} 
				outs.write(b, 0, len);
				copyedPosition +=len;//��д���С
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
		System.err.println(Thread.currentThread().getName()+"�������,��ʼλ��:"+startPosition+"������λ��:"+endPosition+"��������С��"+getCopyedSize() +" ; ��ʱ��"+(end - start )+" ms.");	
	}

}
