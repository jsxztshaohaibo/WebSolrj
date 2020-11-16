package com.jsxztshaohaibo.thread.threadLocals;

public class ThreadAndThreadLocals {
	public static void main(String[] args) throws InterruptedException {
		final ThreadLocal<Integer> tlInteger = new ThreadLocal<Integer>();
		final ThreadLocal<Student> tlStduent = new ThreadLocal<Student>();
		
		
		Thread t1 = new Thread("setValueThread"){
			public void run() {
				System.out.println(Thread.currentThread().getName() +"  初始化前的值 ：tlInteger = "+tlInteger.get() +" ;  tlStduent= "+tlStduent.get());
				System.out.println(Thread.currentThread().getName() +"  开始初始化值...");
				
				tlInteger.set(100);
				Student stu = new Student();
				stu.setName("张三");
				stu.setSex("男");
				stu.setAge("23");
				tlStduent.set(stu);
				System.out.println(Thread.currentThread().getName() +"  初始化值完毕");
				System.out.println(Thread.currentThread().getName() +"  初始化后的值 ：tlInteger = "+tlInteger.get() +" ;  tlStduent= "+tlStduent.get());
			}
		};
		t1.start();
		t1.join();
		Thread t2 = new Thread("getValueThread"){
			public void run() {
				System.out.println(Thread.currentThread().getName() +"  开始执行。。。");
				System.out.println(Thread.currentThread().getName() +"  读取值 ：tlInteger = "+tlInteger.get() +" ;  tlStduent= "+tlStduent.get());
	
			}
		};
		t2.start();
	}
}
class MyThreadLocalTest{
	
	
	ThreadLocal<Integer> tlInteger;
	ThreadLocal<Student> tlStduent ;
	
	
	public void testThreadLocals(){
		System.out.println(Thread.currentThread().getName() +"  tlInteger  ="+tlInteger.get());
		System.out.println(Thread.currentThread().getName() +"  tlStduent  ="+tlStduent.get());
	}

	/**
	 * @return the tlInteger
	 */
	public ThreadLocal<Integer> getTlInteger() {
		return tlInteger;
	}

	/**
	 * @param tlInteger the tlInteger to set
	 */
	public void setTlInteger(ThreadLocal<Integer> tlInteger) {
		this.tlInteger = tlInteger;
	}

	/**
	 * @return the tlStduent
	 */
	public ThreadLocal<Student> getTlStduent() {
		return tlStduent;
	}

	/**
	 * @param tlStduent the tlStduent to set
	 */
	public void setTlStduent(ThreadLocal<Student> tlStduent) {
		this.tlStduent = tlStduent;
	}
	
}
