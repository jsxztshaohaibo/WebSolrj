package com.jsxztshaohaibo.thread.juc;

import java.util.concurrent.CountDownLatch;

/***<pre>
 * 跑步比赛:
 * 1.所有运动员就位；
 * 2.发令员发令
 * 3.所有运动员跑到终点，结束比赛
 * @author Administrator
 *</pre>
 */


public class Test_CountDownLatch_Example {
	
	public static void main(String[] args) {
		int sporters = 5 ;
		// 运动员就续 ，然后发令员发枪
		CountDownLatch readyLatch = new CountDownLatch(5);
		// 发令枪响，运动员统一起跑
		CountDownLatch beginLatch = new CountDownLatch(1);
		// 运动员全部跑完，结束比赛
		CountDownLatch overLatch = new CountDownLatch(sporters);
		
		for(int i = 0; i < sporters; i++){
			new Thread(new Sporter(beginLatch, overLatch)).start();
		}
		
		
		try {
			System.out.println("1秒后统一开始");
			Thread.sleep(1000);
			beginLatch.countDown();
 
			overLatch.await();
			System.out.println("停止比赛");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("全部运动员已完成比赛.....");
	}
}



class Sporter implements Runnable{
	
	private CountDownLatch beginLatch;//执行本线程前，需要等待的前置线程
	
	private CountDownLatch overLatch;//本线程执行完以后，使用的闭锁同步器，

	@Override
	public void run() {
		try{
			System.out.println(Thread.currentThread().getName()+"等待发令枪指示，统一起跑......");
			beginLatch.await();//等待前置线程准备好
			System.out.println(Thread.currentThread().getName()+"发令枪已响，起跑......");
			System.out.println(Thread.currentThread().getName()+" runs over");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			if(overLatch!=null ){
				overLatch.countDown();
			}
		}
	}
	public Sporter(CountDownLatch beginLatch, CountDownLatch overLatch) {
		super();
		this.beginLatch = beginLatch;
		this.overLatch = overLatch;
	}

	public CountDownLatch getBeginLatch() {
		return beginLatch;
	}

	public void setBeginLatch(CountDownLatch beginLatch) {
		this.beginLatch = beginLatch;
	}

	public CountDownLatch getOverLatch() {
		return overLatch;
	}

	public void setOverLatch(CountDownLatch overLatch) {
		this.overLatch = overLatch;
	}
}

