package com.jsxztshaohaibo.thread.synchronizedtest;

/**
 * synchronized –ﬁ Œ∑Ω∑®
 * @author Administrator
 *
 */
public class SynchronizedTest1 {
	    public synchronized void method1(int i ){
	        System.out.println("Method 1 start===="+i);
	        try {
	            System.out.println("Method 1 execute===="+i);
	            Thread.sleep(300);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Method 1 end===="+i);
	    }

	    public synchronized void method2(int  i ){
	        System.out.println("Method 2 start===="+i);
	        try {
	            System.out.println("Method 2 execute===="+i);
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Method 2 end===="+i);
	    }

	    public static void main(String[] args) {
	        final SynchronizedTest1 test = new SynchronizedTest1();

	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	for(int i =0;i<10;i++){
	            		test.method1( i );
	            	}
	            }
	        }).start();

	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	for(int i =0;i<10;i++){
	            		test.method2( i );
	            	}
	            }
	        }).start();
	    }
	}
