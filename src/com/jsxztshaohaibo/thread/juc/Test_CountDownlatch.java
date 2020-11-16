package com.jsxztshaohaibo.thread.juc;

import java.util.concurrent.CountDownLatch;

/**<pre>
 * latch 闭锁：当前线程进行运算时，必须等待其他所有的线程运算完毕以后，当前线程才能继续运算。
 * 
 * 防止该线程中的 CountDownLatch 总数不能正常 减1，所以必须把 CountDownLatch  减 1 (countDown()）的操作 放在 finally 块里执行
 * 
 * CountDownLatch 可以用来，开启多个线程进行运算，最后计算出这些线程运算完成以后，一共耗费了多长时间
 * 
 * 具体的应用：
 * 跑步比赛：
 * 1.发令枪员必须等到所有的参赛者到齐，才能法枪；
 * 2.比赛必须等所有的参赛者跑完比赛才能终止；
 * 
 * join()??
 * @author Administrator
 * </pre>
 *
 */
public class Test_CountDownlatch {
	
	public static void main(String[] args) {
		
		int threadThreshold= 5;
		//CountDownLatch 的初始容量，应该等于要 开启的线程数，要不然会出现死等（容量 > 线程数时）或者提前计算(容量 < 线程数 时)的问题。
		CountDownLatch latch = new CountDownLatch(threadThreshold);
		
		LatchDemo ld = new LatchDemo(latch);
		
		long start = System.currentTimeMillis();
		
		for(int i =1;i<=threadThreshold ; i++){
			new Thread(ld).start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费时间为："+ (end  -  start));
	}
}

class LatchDemo implements Runnable{
	private CountDownLatch latch;
	public LatchDemo(CountDownLatch latch) {
		super();
		this.latch = latch;
	}
	public CountDownLatch getLatch() {
		return latch;
	}
	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
	@Override
	public void run() {
		//执行运行，算出小2000的偶数的和
		try{
			long start = System.currentTimeMillis();
			int count = 0;
			for (int i = 0; i <= 2000; i++) {
				if (i%2==0) {
					count = count+ i ;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("运算开始时间："+  start  +" ；  运算结束时间: "+ end +" ;运算结果count: " + count );
		}finally{
			//防止该线程中的 CountDownLatch 总数不能正常 减1，所以必须把 CountDownLatch  减 1 (countDown()）的操作 放在 finally 块里执行
			if(latch!=null){
				latch.countDown();
			}
		}
		
	}
	
}
