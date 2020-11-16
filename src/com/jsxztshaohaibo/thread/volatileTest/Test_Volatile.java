package com.jsxztshaohaibo.thread.volatileTest;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * volatile �ؼ��֣���֤�����εĹ���������������߳��е��޸��ǿɼ���
 * 
 * 1.ע�⣺
 * volatile ���߱������ԣ�����߳̿���ͬʱ�Ա������в�����
 * volatile ���ܱ�֤ԭ����
 * 
 * 
 * ��֤ԭ���� ���Բ��� AtomicInteger ԭ�Ӱ�װ��
 * Atomic ԭ�Ӱ�װ�࣬����ͨ��CAS�㷨����֤ԭ�Ӳ����ġ�
 * CAS�㷨��compare  and swap
 * CAS ����������������
 * �ڴ�ֵ��V
 * Ԥ��ֵ��A
 * ����ֵ��B
 * ���ҽ���  V = A ʱ���Ÿ��� V = B���������κβ�����
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
				System.out.println("����false");
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
			 * ����˯��ʱ�䳤һЩ���Ϳ����������߳�ִ�е�getFlag(),
			 * �п��ܻ�õ�false�Ľ����
			 * �����߳�ִ���޸�flag=true�����̻߳����̵õ� true�Ľ��
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
		return serialNmu++;//��Ȼ���� volatie ���Σ����ǻ��ǻ�������⣬ ++ ��������ԭ�Ӳ���
	}
	public void setSerialNmu(int serialNmu) {
		this.serialNmu = serialNmu;
	}
	
	

}
