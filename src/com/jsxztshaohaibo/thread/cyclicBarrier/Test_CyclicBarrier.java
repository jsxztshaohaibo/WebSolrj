package com.jsxztshaohaibo.thread.cyclicBarrier;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * CyclicBarrier >>>>所有的线程必须同时到达栅栏位置，才能继续执行。
 * 每个线程使用await()方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 * 也就是阻塞一组线程，直到 CyclicBarrier 指定的线程数量 都执行了await()方法， 所有的线程，才开始一起执行。
 * 调用await方法的线程告诉CyclicBarrier自己已经到达同步点，然后当前线程被阻塞。
 * 直到parties个参与线程调用了await方法
 * 
 * N个线程相互等待，所有的线程都必须等待。
 * CyclicBarrier更像一个水闸, 线程执行就像水流, 在水闸处都会堵住, 等到水满(线程到齐)了, 才开始泄流<br/>
 *
 * <font color="red">可以应用于 多线程 同时去插入、修改数据库，但是都不提交，只等所有的线程都处理完了，才统一提交</font>
 * @author Administrator
 *
 */
public class Test_CyclicBarrier {

	public static void main(String[] args) {
		int threadCount = 3;
		CountDownLatch cdLatch = new CountDownLatch(threadCount);
		ConcurrentHashMap<String,Integer> cMap = new ConcurrentHashMap<String, Integer>();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);
		
		for (int i = 0; i < threadCount; i++) {
			
			Worker worker = new Worker(cyclicBarrier,"工作线程" + i,cMap ,cdLatch);
			worker.start();
		}
		try {
			System.out.println("等待工作线程执行完毕，再进行加和计算...");
			cdLatch.await();
			System.out.println("计算...");
			int count =0;
			System.out.println(cMap.size());
			Set<Entry<String, Integer>> entrySet = cMap.entrySet();
			Iterator<Entry<String, Integer>> iterator = entrySet.iterator();
			while(iterator.hasNext()){
				Entry<String, Integer> next = iterator.next();
				Integer value = next.getValue();
				
				count +=value;
			}
			System.out.println("一共处理："+count+" 条数据!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}

class Worker extends Thread {
	private CyclicBarrier cyclicBarrier;
	private ConcurrentHashMap<String,Integer> cMap ;
	private CountDownLatch cdLatch;
	public Worker(CyclicBarrier cyclicBarrier,String threadName ,ConcurrentHashMap<String,Integer> cMap,CountDownLatch cdLatch) {
		super(threadName);
		this.cyclicBarrier = cyclicBarrier;
		this.cMap=cMap;
		this.cdLatch=cdLatch;
		System.out.println("创建了>>>>"+threadName);
	}
	
	@Override
	public void run() {
		try {
			Random random = new Random();
			int count=random.nextInt(5000);

			System.out.println(Thread.currentThread().getName() + "开始等待其他线程写库，写了 "+count+" 条数据，但不提交");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + "开始执行提交");
			Thread.sleep(count);// 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
			
			cMap.put(Thread.currentThread().getName(), count);
			System.out.println(Thread.currentThread().getName() + "执行提交完毕!");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cdLatch.countDown();
		}
	}
}