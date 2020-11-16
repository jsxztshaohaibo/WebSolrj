package com.jsxztshaohaibo.thread;

public class TestClass {
	
	private  static boolean isTrue= true;
	static class MyThread extends Thread{
		int i=0;
		public void run() {
			System.out.println(this.getClass().getName());
			while(isTrue){
				synchronized(this){
					System.out.println(i++);
				}
			}
			System.out.println(i);
		}
	}
	
	public static void main(String[] args ) throws Exception{
		/*MyThread mt = new MyThread();
		mt.start();
		Thread.sleep(2000);
		isTrue=false;
		System.out.println(isTrue);
		System.out.println("Done.");*/
		
	//==============================================//
		
		MyThread2 mt2 = new MyThread2();
		mt2.start();
		Thread.sleep(2000);
		isTrue_volatile=false;
		System.out.println(isTrue_volatile);
		System.out.println("Done....");
	}


	private volatile  static boolean isTrue_volatile= true;
	static class MyThread2 extends Thread{
		int i=0;
		@Override
		public void run() {
			while(isTrue_volatile){
				System.out.println(i++);
			}
			System.out.println(i);
		}
	}

}
