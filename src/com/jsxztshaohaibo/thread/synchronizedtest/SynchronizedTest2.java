package com.jsxztshaohaibo.thread.synchronizedtest;


/**
 * synchronized ÐÞÊÎ´úÂë¿é
 * @author Administrator
 *
 */
public class SynchronizedTest2 {
	
	int i ;
	public SynchronizedTest2(int _i){
		this.i =_i;
	}
	public SynchronizedTest2(){
	}
    public void method1(){
        System.out.println("Method 1 start");
        try {
            synchronized (this) {
            	i++;
                System.out.println("Method 1 execute    "+i);
                Thread.sleep(300);
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
            	i++;
                System.out.println("Method 2 execute  "+i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }

    public static void main(String[] args) {
    	int i = 5;
        final SynchronizedTest2 test = new SynchronizedTest2(i);

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