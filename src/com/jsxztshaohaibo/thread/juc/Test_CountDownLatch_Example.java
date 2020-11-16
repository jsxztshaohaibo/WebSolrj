package com.jsxztshaohaibo.thread.juc;

import java.util.concurrent.CountDownLatch;

/***<pre>
 * �ܲ�����:
 * 1.�����˶�Ա��λ��
 * 2.����Ա����
 * 3.�����˶�Ա�ܵ��յ㣬��������
 * @author Administrator
 *</pre>
 */


public class Test_CountDownLatch_Example {
	
	public static void main(String[] args) {
		int sporters = 5 ;
		// �˶�Ա���� ��Ȼ����Ա��ǹ
		CountDownLatch readyLatch = new CountDownLatch(5);
		// ����ǹ�죬�˶�Աͳһ����
		CountDownLatch beginLatch = new CountDownLatch(1);
		// �˶�Աȫ�����꣬��������
		CountDownLatch overLatch = new CountDownLatch(sporters);
		
		for(int i = 0; i < sporters; i++){
			new Thread(new Sporter(beginLatch, overLatch)).start();
		}
		
		
		try {
			System.out.println("1���ͳһ��ʼ");
			Thread.sleep(1000);
			beginLatch.countDown();
 
			overLatch.await();
			System.out.println("ֹͣ����");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("ȫ���˶�Ա����ɱ���.....");
	}
}



class Sporter implements Runnable{
	
	private CountDownLatch beginLatch;//ִ�б��߳�ǰ����Ҫ�ȴ���ǰ���߳�
	
	private CountDownLatch overLatch;//���߳�ִ�����Ժ�ʹ�õı���ͬ������

	@Override
	public void run() {
		try{
			System.out.println(Thread.currentThread().getName()+"�ȴ�����ǹָʾ��ͳһ����......");
			beginLatch.await();//�ȴ�ǰ���߳�׼����
			System.out.println(Thread.currentThread().getName()+"����ǹ���죬����......");
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

