package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.io.IOException;
/**
 * ���̸߳����ļ�ʵ�ֲ���:<br/>
 * 1.Դ�ļ��ֿ飺<br/>
 * 		���̸߳�������ÿ��С�߳�����Ҫ�����ļ��Ĵ�С�����߳���Ϊ�������ļ��ܴ�С/�߳���  Ϊÿ���̸߳��ƵĴ�С��<br/>
 * 		ȷ��ÿ���̸߳����ļ�ʱ����ʼλ�úͽ���λ�ã���һ���߳���0�����С���˺��̵߳Ŀ�ʼλ����ǰһ���̵߳Ľ���λ�ã�����λ���ǿ�ʼλ��+���С����<br/>
 * 		��Ҫע����ǣ����һ���̵߳Ľ���λ�����ļ��Ĵ�С������Ϊ�ļ��ܴ�С/�߳������ܳ���С����<br/>
 * 2.�����̣߳�<br/>
 * 		ÿ�������߳�ѭ�����Ƶ�ʱ����Ҫ�ж��ж������������Ѿ����Ƶ�λ�ò����ڽ���λ�ã�����Ѿ����ڣ�����Ҫ�����д��Ĵ�С����Ϊ ����λ��-�Ѹ��Ƶ�λ�ã�<br/>
 * 		�����Ѿ��������ļ��Ĵ�С(����λ��-��ʼλ��)<br/>
 * 3.ͳ���̣߳�<br/>
 * 		����ʵʱѭ��ͳ��ÿ���̸߳��ƵĽ��ȣ�������Ҫ�ڴ�����ͳ���߳�ʱ����ÿ���̷߳�װ��һ�������д��ݸ����̣߳�<br/>
 * 		���ÿ���̸߳��ƵĴ�С���ܺ�=�ļ��Ĵ�С��������ļ��Ѹ�����ɣ����˳�ͳ��ѭ����<br/>
 * 		���м�����ļ�����Ϊ���յ��ļ������б�Ҫ�����ʹ��JDK�� File#renameTo()����������Ҫע��Դ�����ļ�ϵͳ�ĸ�ʽ�����Ŀ������ļ�ϵͳ�ĸ�ʽһ�£������renameToʧ�ܡ�<br/>
 * @author Administrator
 *
 */
public class MainThread {
	/**
	 * ����Դ�ļ���Ŀ���ļ��С��ļ����еĸ�������������
	 * @param soucreFile Դ�ļ�
	 * @param destPath   Ŀ���ļ���
	 * @param splitCount ��ֵ��ļ�����(Ҳ������Ҫ�������̸߳���)
	 */
	public static void startWork(File soucreFile,String destPath,int splitCount){
		if(!soucreFile.exists()){
			System.out.println("Դ�ļ������ڡ�");
			return ;
		}
		if(soucreFile.isDirectory()){
			System.out.println("Դ�ļ�������Ŀ¼��");
			return;
		}
		if(splitCount<=0){
			System.out.println("�ļ����и�����������(ֻ��Ϊ����0������)��");
			return ;
		}
		System.out.println("Դ�ļ���С��"+soucreFile.length());
		//����Ŀ���ļ���
		File destFilePath =  new File(destPath);
		if(!destFilePath.exists()){
			destFilePath.mkdirs();
		}
		//��ȡԴ�ļ����ļ���
		String fileName = soucreFile.getName();
		
		//Ŀ���ļ�ȫ��Ϣ
		String destFileName = destPath+File.separator+fileName;
		File destFile = new File(destFileName);
		
		//�����ļ���(δ��ȫ������ʱ���ļ�����)
		String tmpFileName = destFileName +".tmp";
		File tmpFile = new  File(tmpFileName); 
		if(!tmpFile.exists()){
			try {
				tmpFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//��ȡԴ�ļ��Ĵ�С
		long fileSize = soucreFile.length();
		//����Դ�ļ���С���зָ����������ÿ�ݵĴ�С
		long perSize = fileSize/splitCount;
		
		System.out.println( "ÿ�ݴ�С��"+perSize);
		//��һ������Ŀ�ʼλ�á�����λ��(�Ժ�ÿ������Ŀ�ʼλ�� ����  ǰһ������Ľ���λ�� ������λ�þ��ǿ�ʼλ��+ ÿ�ݵĴ�С)
		//���һ������Ľ���λ�����ļ��Ĵ�С
		long startPosition = 0L;
		long endPosition =perSize;//startPosition+perSize;
		
		//�ŵ��������������ͳ��ÿ���߳��ѿ�������������
		CopyerThread[] cts = new CopyerThread[splitCount];
		
		for(int i  = 0 ;i<splitCount;i++){
			String threadName = "CopyerThread"+(i+1);
			System.out.println(threadName+" ׼����ʼ��λ�ã�"+startPosition+"��׼��������λ�ã�"+endPosition +",׼��д��Ĵ�С:"+(endPosition-startPosition));
			
			CopyerThread cp = new  CopyerThread(soucreFile ,tmpFile,startPosition,endPosition);
			//�ŵ��������������ͳ��ÿ���߳��ѿ�������������
			cts[i] = cp;
			
			new Thread(cp,threadName).start();
			
			//��һ�ο�ʼ��λ��
			startPosition = endPosition ;
			//��һ�ν�����λ��
			endPosition = startPosition + perSize;
			
			if(i == (splitCount-2)){//����ǵ����ڶ��Σ�����һ��(Ҳ�������һ��)����λ�����ļ��Ĵ�С
				endPosition = fileSize;
			}
			
		}
		
		StatisticsThread statThread = new StatisticsThread("statThread", fileSize, cts,tmpFile,destFile );
		statThread.start();
		
		
	}
	
}
