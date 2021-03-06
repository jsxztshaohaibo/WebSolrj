package com.jsxztshaohaibo.thread.synchronizedtest;


public class SynchronizedTest3 {
    public void method1(){
        System.out.println("Method 1 start");
        try {
            synchronized (this) {
                System.out.println("Method 1 execute");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public void method2(){
        System.out.println("Method 2 start");
        try {
            synchronized (this) {
                System.out.println("Method 2 execute");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }

    public static void main(String[] args) {
        final SynchronizedTest3 test = new SynchronizedTest3();

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