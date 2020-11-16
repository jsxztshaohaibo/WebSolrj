package com.jsxztshaohaibo.thread.countLatch;

import java.util.concurrent.CountDownLatch;

/**CountDownLatch : 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行
 * 
 * CountDownLatch 是倒数计数器, 线程完成一个就记一个, 就像 报数一样, 只不过是递减的.
 * 
 * 统计 10个线程全部运行完所耗费的时间
 * @author Administrator
 *
 */

public class StatWorkTime {

	/**
	 * CountDownLatch实现====>>>>倒数计数器
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int threadCount = 5;
		CountDownLatch cdLatch = new CountDownLatch(threadCount);//总线程数，每个线程运算完以后，conutDown()一下，递减 1 
		for(int i=1;i<=threadCount;i++){
			MyRunnable mr = new MyRunnable(cdLatch);
			
			Thread t = new Thread(mr,"线程"+i);
			t.start();
		}
		try {
			cdLatch.await();//阻塞等待；等待其他线程中countDown 等于 0 时，会被唤醒，继续往下执行
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+ (end-start));
	}
	
	
	
	
	/**
	 * Thread.activeCount()实现====>>>>获取当前 【线程组】中活动的线程数
	 * @param args
	 */
	/*public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for(int i=1;i<=5;i++){
			MyRunnable mr = new MyRunnable();
			
			Thread t = new Thread(mr,"线程"+i);
			t.start();
		}
		while(Thread.activeCount()>0){
			if(Thread.activeCount()==1){
				break;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+ (end-start));
	}*/

}

/**
 * 循环5W次，打印出 100的倍数最后一个数
 * @author Administrator
 *
 */
class MyRunnable implements Runnable{
	
	private CountDownLatch cdLatch;
	
	/**
	 * @return the cdLatch
	 */
	public CountDownLatch getCdLatch() {
		return cdLatch;
	}

	/**
	 * @param cdLatch the cdLatch to set
	 */
	public void setCdLatch(CountDownLatch cdLatch) {
		this.cdLatch = cdLatch;
	}
	public MyRunnable(CountDownLatch cdLatch){
		this.cdLatch = cdLatch;
	}
	@Override
	public void run() {
		try {
			for(int i=1;i<=50000;i++){
				if(i%100==0){
					System.out.println(Thread.currentThread().getName()+"======" + i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cdLatch.countDown();
		}
		
	}
}