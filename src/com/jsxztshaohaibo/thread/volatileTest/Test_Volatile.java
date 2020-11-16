package com.jsxztshaohaibo.thread.volatileTest;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * volatile 关键字，保证被修饰的共享变量，对其他线程中的修改是可见的
 * 
 * 1.注意：
 * volatile 不具备互斥性（多个线程可以同时对变量进行操作）
 * volatile 不能保证原子性
 * 
 * 
 * 保证原子性 可以采用 AtomicInteger 原子包装类
 * Atomic 原子包装类，都是通过CAS算法来保证原子操作的。
 * CAS算法：compare  and swap
 * CAS 包含三个操作数：
 * 内存值：V
 * 预估值：A
 * 更新值：B
 * 当且仅当  V = A 时，才更新 V = B，否则不做任何操作。
 * 		
 * @author Administrator
 *
 */
public class Test_Volatile {
	@Test
	public void testVolatile() throws Exception{
		MyThread mt = new MyThread();
		Thread t = new Thread(mt);
		t.start();
		while(true){
			if (mt.getFlag()) {
				System.out.println("----------------------------------");
				break;
			}else{
				System.out.println("还是false");
			}
		}
	}
	
	
	public static void main(String[] args) {
		AtomicDemo ad = new AtomicDemo();
		for (int i = 0; i <1000; i++) {
			new Thread(ad).start();
		}
	}
}

class MyThread implements Runnable{
	
	private volatile boolean flag;
	public void run() {
		try {
			/**
			 * 设置睡眠时间长一些，就可以先让主线程执行到getFlag(),
			 * 有可能会得到false的结果，
			 * 随后该线程执行修改flag=true，主线程会立刻得到 true的结果
			 */
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.flag= true;
		System.out.println("flag = " + flag);
	}
	
	
	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}

class AtomicDemo  implements Runnable{
	
	private  volatile int serialNmu = 0;
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" : "+ this.getSerialNmu());
	}
	public int getSerialNmu() {
		return serialNmu++;//虽然加了 volatie 修饰，但是还是会出现问题， ++ 操作不是原子操作
	}
	public void setSerialNmu(int serialNmu) {
		this.serialNmu = serialNmu;
	}
	
	

}
