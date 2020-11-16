package com.jsxztshaohaibo.thread.multipleThreadCopy;

import java.io.File;
import java.io.IOException;
/**
 * 多线程复制文件实现步骤:<br/>
 * 1.源文件分块：<br/>
 * 		主线程负责计算出每个小线程现需要复制文件的大小，以线程数为块数，文件总大小/线程数  为每个线程复制的大小；<br/>
 * 		确定每个线程复制文件时的起始位置和结束位置（第一个线程是0到块大小，此后线程的开始位置是前一个线程的结束位置，结束位置是开始位置+块大小），<br/>
 * 		需要注意的是：最后一个线程的结束位置是文件的大小（是因为文件总大小/线程数可能出现小数）<br/>
 * 2.复制线程：<br/>
 * 		每个复制线程循环复制的时候，需要判断有读到的数据且已经复制的位置不大于结束位置，如果已经大于，则需要把最后写入的大小更改为 结束位置-已复制的位置；<br/>
 * 		计算已经复制了文件的大小(复制位置-起始位置)<br/>
 * 3.统计线程：<br/>
 * 		负责实时循环统计每个线程复制的进度，所以需要在创建该统计线程时，把每个线程封装到一个数组中传递给该线程；<br/>
 * 		如果每个线程复制的大小的总和=文件的大小，则表明文件已复制完成，则退出统计循环，<br/>
 * 		把中间过程文件更名为最终的文件（如有必要）如果使用JDK的 File#renameTo()方法，则需要注意源磁盘文件系统的格式必须和目标磁盘文件系统的格式一致，否则会renameTo失败。<br/>
 * @author Administrator
 *
 */
public class MainThread {
	/**
	 * 根据源文件、目标文件夹、文件分切的个数来启动任务
	 * @param soucreFile 源文件
	 * @param destPath   目标文件夹
	 * @param splitCount 拆分的文件个数(也就是需要创建的线程个数)
	 */
	public static void startWork(File soucreFile,String destPath,int splitCount){
		if(!soucreFile.exists()){
			System.out.println("源文件不存在。");
			return ;
		}
		if(soucreFile.isDirectory()){
			System.out.println("源文件不能是目录。");
			return;
		}
		if(splitCount<=0){
			System.out.println("文件分切个数参数错误(只能为大于0的整数)。");
			return ;
		}
		System.out.println("源文件大小："+soucreFile.length());
		//创建目标文件夹
		File destFilePath =  new File(destPath);
		if(!destFilePath.exists()){
			destFilePath.mkdirs();
		}
		//获取源文件的文件名
		String fileName = soucreFile.getName();
		
		//目标文件全信息
		String destFileName = destPath+File.separator+fileName;
		File destFile = new File(destFileName);
		
		//过程文件名(未完全拷贝完时的文件名称)
		String tmpFileName = destFileName +".tmp";
		File tmpFile = new  File(tmpFileName); 
		if(!tmpFile.exists()){
			try {
				tmpFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//获取源文件的大小
		long fileSize = soucreFile.length();
		//根据源文件大小和切分个数，计算出每份的大小
		long perSize = fileSize/splitCount;
		
		System.out.println( "每份大小："+perSize);
		//第一个任务的开始位置、结束位置(以后每个任务的开始位置 就是  前一个任务的结束位置 ，结束位置就是开始位置+ 每份的大小)
		//最后一个任务的结束位置是文件的大小
		long startPosition = 0L;
		long endPosition =perSize;//startPosition+perSize;
		
		//放到任务数组里，方便统计每个线程已拷贝多少数据量
		CopyerThread[] cts = new CopyerThread[splitCount];
		
		for(int i  = 0 ;i<splitCount;i++){
			String threadName = "CopyerThread"+(i+1);
			System.out.println(threadName+" 准备开始的位置："+startPosition+"，准备结束的位置："+endPosition +",准备写入的大小:"+(endPosition-startPosition));
			
			CopyerThread cp = new  CopyerThread(soucreFile ,tmpFile,startPosition,endPosition);
			//放到任务数组里，方便统计每个线程已拷贝多少数据量
			cts[i] = cp;
			
			new Thread(cp,threadName).start();
			
			//下一次开始的位置
			startPosition = endPosition ;
			//下一次结束的位置
			endPosition = startPosition + perSize;
			
			if(i == (splitCount-2)){//如果是倒数第二次，则下一次(也就是最后一次)结束位置是文件的大小
				endPosition = fileSize;
			}
			
		}
		
		StatisticsThread statThread = new StatisticsThread("statThread", fileSize, cts,tmpFile,destFile );
		statThread.start();
		
		
	}
	
}
