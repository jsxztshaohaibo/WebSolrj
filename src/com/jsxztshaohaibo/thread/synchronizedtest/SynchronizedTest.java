package com.jsxztshaohaibo.thread.synchronizedtest;


/**
 * volatile¹Ø¼ü×Ö
 * @author Administrator
 *
 */
public class SynchronizedTest {
	
	volatile int count; 
	
	public SynchronizedTest(int _i){
		this.count =_i;
	}
	public SynchronizedTest(){
	}
	
    public void method1(){
        System.out.println("Method 1 start");
        count=4;
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1   "+count);
        System.out.println("Method 1 end");
    }

    public void method2(){
        System.out.println("Method 2 start");
        count=5;
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2   "+count);
        System.out.println("Method 2 end");
    }

    public static void main(String[] args) {
        final SynchronizedTest test = new SynchronizedTest(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
            	for(int j=0;j<10;j++){
            		test.method1();
            	}
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
            	for(int j=0;j<10;j++){
            		test.method2();
            	}
            }
        }).start();
    }
}