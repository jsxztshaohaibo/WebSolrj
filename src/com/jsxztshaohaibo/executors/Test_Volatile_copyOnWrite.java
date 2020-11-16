package com.jsxztshaohaibo.executors;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * CopyOnWrite思想-------比加读写锁的效率高
 * 适应于  >>>>>>  写操作频率很低，读操作频率很高，是写少读多的场景。
 * 写的时候，是利用内部真正存储数据的变量的副本进行真正的写操作；
 * 写完以后，将副本再赋值给带有 volatile修饰的真正的变量 
 * 
 * @author Administrator
 *
 */
public class Test_Volatile_copyOnWrite{
	
	
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		CopyOnWriteArrayList<String> copList = new CopyOnWriteArrayList<String>();
		for(int i=1;i<=100;i++){
			copList.add("00"+i);
		}
		CountDownLatch cdLatchAll =new CountDownLatch(2);
		
		CountDownLatch cdLatch =new CountDownLatch(1);
		
		WriteThread wrtie= new WriteThread(copList,cdLatch,cdLatchAll);
		ReadThread read= new ReadThread(copList,cdLatch,cdLatchAll);
		
		Thread wThread = new Thread( wrtie, "write");
		Thread rThread = new Thread( read, "read");
		
		wThread.start();
		rThread.start();
		
		
		try {
			cdLatchAll.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println( new Date(end) + "耗时："+(end - start));
	}
	
}
class WriteThread implements  Runnable {
	CopyOnWriteArrayList<String> copList ;
	CountDownLatch cdLatch ;
	CountDownLatch cdLatchAll ;
	public  WriteThread(CopyOnWriteArrayList<String> copList,CountDownLatch cdLatch ,CountDownLatch cdLatchAll ){
		this.copList =copList;
		this.cdLatch=cdLatch;
		this.cdLatchAll =cdLatchAll;
	}
	public void run() {
		try {
			
			System.out.println("read___hashCode:"+copList.hashCode());
			Thread.sleep(5000);
			System.out.println("我在等待...");
			cdLatch.await();
			System.out.println("read___hashCode:"+copList.hashCode());
			System.out.println(copList.get(0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			cdLatchAll.countDown();
		}
		
		
	}
} 

class ReadThread implements  Runnable {
	CopyOnWriteArrayList<String> copList ;
	CountDownLatch cdLatch ;
	CountDownLatch cdLatchAll ;
	public  ReadThread(CopyOnWriteArrayList<String> copList,CountDownLatch cdLatch,CountDownLatch cdLatchAll   ){
		this.copList =copList;
		this.cdLatch=cdLatch;
		this.cdLatchAll =cdLatchAll;
	}
	public void run() {
		try {
			System.out.println("我准备睡醒了再删....");
			System.out.println("delete_____hashCode:"+copList.hashCode());
			//copList.remove("001");
			copList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cdLatch.countDown();
			System.out.println("我删除完了...");
			System.out.println("delete_____hashCode:"+copList.hashCode());
			cdLatchAll.countDown();
		}
	}	
} 