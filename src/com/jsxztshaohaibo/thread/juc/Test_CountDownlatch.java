package com.jsxztshaohaibo.thread.juc;

import java.util.concurrent.CountDownLatch;

/**<pre>
 * latch ��������ǰ�߳̽�������ʱ������ȴ��������е��߳���������Ժ󣬵�ǰ�̲߳��ܼ������㡣
 * 
 * ��ֹ���߳��е� CountDownLatch ������������ ��1�����Ա���� CountDownLatch  �� 1 (countDown()���Ĳ��� ���� finally ����ִ��
 * 
 * CountDownLatch ������������������߳̽������㣬���������Щ�߳���������Ժ�һ���ķ��˶೤ʱ��
 * 
 * �����Ӧ�ã�
 * �ܲ�������
 * 1.����ǹԱ����ȵ����еĲ����ߵ��룬���ܷ�ǹ��
 * 2.������������еĲ������������������ֹ��
 * 
 * join()??
 * @author Administrator
 * </pre>
 *
 */
public class Test_CountDownlatch {
	
	public static void main(String[] args) {
		
		int threadThreshold= 5;
		//CountDownLatch �ĳ�ʼ������Ӧ�õ���Ҫ �������߳�����Ҫ��Ȼ��������ȣ����� > �߳���ʱ��������ǰ����(���� < �߳��� ʱ)�����⡣
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
		
		System.out.println("�ķ�ʱ��Ϊ��"+ (end  -  start));
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
		//ִ�����У����С2000��ż���ĺ�
		try{
			long start = System.currentTimeMillis();
			int count = 0;
			for (int i = 0; i <= 2000; i++) {
				if (i%2==0) {
					count = count+ i ;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("���㿪ʼʱ�䣺"+  start  +" ��  �������ʱ��: "+ end +" ;������count: " + count );
		}finally{
			//��ֹ���߳��е� CountDownLatch ������������ ��1�����Ա���� CountDownLatch  �� 1 (countDown()���Ĳ��� ���� finally ����ִ��
			if(latch!=null){
				latch.countDown();
			}
		}
		
	}
	
}
