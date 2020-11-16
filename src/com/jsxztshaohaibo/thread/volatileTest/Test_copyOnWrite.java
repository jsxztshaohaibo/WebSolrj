package com.jsxztshaohaibo.thread.volatileTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * CopyOnWrite思想-------比加读写锁的效率高
 * 适应于  >>>>>>  写操作频率很低，读操作频率很高，是写少读多的场景。
 * 写的时候，是利用内部真正存储数据的变量的副本进行真正的写操作；
 * 写完以后，将副本再赋值给带有 volatile修饰的真正的变量 
 * 
 * @author Administrator
 *
 */
public class Test_copyOnWrite{
	
	private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static Lock writeLock = rwLock.writeLock();
	private static Lock readLock = rwLock.readLock();
	private static final int THREAD_READ_COUNT = 4;
	private static final int THREAD_WRITE_COUNT =1;
	private static CountDownLatch cdLatch = new CountDownLatch(THREAD_READ_COUNT+THREAD_WRITE_COUNT);
	static volatile List<String> list = new ArrayList<String>(600000);
	private static String currentThreadName="";
	
	
	
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		CopyOnWriteArrayList<String> copList = new CopyOnWriteArrayList<String>();
		for(int i=1;i<=200;i++){
			copList.add("00"+i);
		}
		
		
		for(int i=1;i<=200;i++){
			list.add("00"+i);
		}
		
		//开启读线程
		for(int i=1;i<=THREAD_READ_COUNT;i++){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Test_copyOnWrite.read(list);
				}
			} , "ThreadRead_"+i);
			thread.start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//开启写线程
		for(int i =1;i<=THREAD_WRITE_COUNT;i++){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Test_copyOnWrite.write(list);
				}
			} , "ThreadWrite_"+i);
			thread.start();
		}
		
		
		try {
			cdLatch.await();//等待，读完、写完，再计时
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println( new Date(end) + "耗时："+(end - start));
	}
	
	public static void write(List<String> list){
		try {
			writeLock.lock();
			System.out.println(Thread.currentThread().getName()+"开始写入....");
			for(int i =201;i<=500000;i++){
				list.add("00" +i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cdLatch.countDown();
			writeLock.unlock();
		}
		System.out.println(Thread.currentThread().getName()+"写入完成....");
	}
	
	public  static void read(List<String> list){
		try {
			readLock.lock();
			currentThreadName=Thread.currentThread().getName();
			Thread.sleep(5000);
			for(int i=0;i<list.size();i++){
				System.out.println(Thread.currentThread().getName()+"===" + (list.get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cdLatch.countDown();
			readLock.unlock();
		}
	}
	
}
