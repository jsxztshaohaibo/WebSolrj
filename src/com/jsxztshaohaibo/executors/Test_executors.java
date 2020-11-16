package com.jsxztshaohaibo.executors;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;



/**<pre>
 *  确定创建多少线程数才是合理-《java虚拟机并发编程》
 *  
 *  三个好处。
 *  第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 *  第二：提高响应速度。当任务到达时，任务可以不需要的等到线程创建就能立即执行。
 *  第三：提高线程的可管理性。
 *  线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，
 *  还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控
 *  
 *  
 *  在Java中，我们一般通过继承hread类和实现Runnnable接口，调用线程的start()方法实现线程的启动。
 *  但如果并发的数量很多，而且每个线程都是执行很短的时间便结束了，
 *  那样频繁的创建线程和销毁进程会大大的降低系统运行的效率。
 *  
 *  线程池正是为了解决多线程效率低的问题而产生的，他使得线程可以被复用，
 *  就是线程执行结束后不被销毁，而是可以继续执行其他任务
 *  
 *  1、new Thread的弊端
		执行一个异步任务你还只是如下new Thread吗？
		new Thread(new Runnable() {
		    public void run() {
		    }
		}).start();
		new Thread的弊端如下：
		a. 每次new Thread新建对象性能差。
		b. 线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机或oom。
		c. 缺乏更多功能，如定时执行、定期执行、线程中断。
		
		相比new Thread，Java提供的四种线程池的好处在于：
		a. 重用存在的线程，减少对象创建、消亡的开销，性能佳。
		b. 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
		c. 提供定时执行、定期执行、单线程、并发数控制等功能。
		
		
		在创建了线程池后，线程中没有任何线程，等到有任务到来时才创建线程去执行任务。
		默认情况下在创建了线程池后，线程池中的线程数为0，除非调用了 ThreadPoolExecutor 的prestartCoreThread()方法。
		(这里假设没有调用prestartCoreThread()，那么一开始的时候线程的个数为0。)
		
		当有任务来之后，就会创建一个线程去执行任务，
		当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中.


corePoolSize，核心线程数的个数。 
		当线程池被创建时，默认的情况下是没有线程产生的，除非调用了prestartCoreThread()的方法。
		这里假设没有调用prestartCoreThread()，那么一开始的时候线程的个数为0。

		直到有任务进来，每一个任务进来，就会对应一个线程的产生。
		当线程的个数达到corePoolSize核心线程的个数时，会把任务放到等待队列中去，
		queueCapacity表示该队列可以存放的任务个数大小。

		当等待队列中的任务个数达到queueCapacity的时候，并且当前线程个数是大于等于corePoolSize核心线程数时，
		线程的个数就会再次增加，一直增加到maxPoolSize的值。

		当线程个数已经达到maxPollSize时，新进来的任务会被拒绝处理然后抛出异常。

		当任务并不是很多的时候，线程就会处于空闲状态。
		当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。
		如果allowCoreThreadTimeout设置为true，则核心线程也会推出，即所有线程均会退出直到线程数量为0。

		处理任务的优先级为： 
		1. 核心线程corePoolSize > 阻塞队列workQueue > 最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
				a.根据任务数， 逐渐创建线程直到 达到corePoolSize指定的核心线程个数；
				b.如果线程数已经达到corePoolSize指定的个数，还有任务到来，则就放入阻塞队列里，等候有的核心线程空闲下来处理；
				c.如果阻塞队列也满了，则继续创建非核心线程来处理队列里保存的任务；
				d.如果创建的非核心线程数达到了maximumPoolSize指定的个数，队列也满了，则就根据handler处理被拒绝的任务。
				
		2. 当池中的线程数大于corePoolSize的时候，多余的空闲非核心线程会等待keepAliveTime长的时间，
		       如果无请求处理，就自行销毁。
		       若线程池通过ThreadPoolExecutor#allowCoreThreadTimeOut() 方法设置核心线程空闲超时 属性为 true，
		       则该keepAliveTime时长同样会作用于核心线程。
 * keepAliveTime
		非核心线程闲置时的超时时长。超过该时长，非核心线程就会被回收。
		若线程池通过ThreadPoolExecutor#allowCoreThreadTimeOut() 方法设置 allowCoreThreadTimeOut 属性为 true，
		则该时长同样会作用于核心线程。
 * 
 * @author Administrator
 *
 *Executor  (interface)
 *   |--ExecutorService  (interface)
 *   		|--AbstractExecutorService  (abstract)
 *   				|--ThreadPoolExecutor   (class)
 *   		|--ScheduledExecutorService    (interface)
 *					|--ScheduledThreadPoolExecutor (class)
 *</pre>
 *
 */
public class Test_executors {
	public static void main(String[] args) {
		// 1.我们可以先获取到系统可用的处理器核心数：
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("可用的处理器核心数:"+availableProcessors);
	}
	public void exec(){
		 /*public ThreadPoolExecutor(int corePoolSize,  int maximumPoolSize,
                 long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
				this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				Executors.defaultThreadFactory(), defaultHandler);
		}
		 public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, 
		 		 long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                 ThreadFactory threadFactory, RejectedExecutionHandler handler) {
				if (corePoolSize < 0 || maximumPoolSize <= 0 || maximumPoolSize < corePoolSize || keepAliveTime < 0)
					throw new IllegalArgumentException();
				if (workQueue == null || threadFactory == null || handler == null)
					throw new NullPointerException();
				this.corePoolSize = corePoolSize;
				this.maximumPoolSize = maximumPoolSize;
				this.workQueue = workQueue;
				this.keepAliveTime = unit.toNanos(keepAliveTime);
				this.threadFactory = threadFactory;
				this.handler = handler;
		}*/
		
		/**
		 * Executors.newFixedThreadPool(10)
		 * 创建了一个核心线程数为传入的nThreads，最大线程数也为nThreads，存活时间为0秒。
		        队列为LinkedBlockingQueue来保存队列任务。
		        
			这种情况下每来一个任务首先看线程池数量有没有到nThreads，
			如果没有达到则创建一个新的线程并执行，否则加入队列中，等待执行。
			此处LinkedBlockingQueue没有指定大小即最多可以接收Integer.MAX_VALUE个缓冲任务。
			相当于创建了nThreads个线程来执行队列中的任务。*/
		Executor executor = Executors.newFixedThreadPool(10);
		//executor.execute(null);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		//executorService.execute(null);
		//executorService.submit(null, null);
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
			}
		});
		
		
		/* public static ExecutorService newFixedThreadPool(int nThreads) {
		        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
		                                      new LinkedBlockingQueue<Runnable>());//LinkedBlockingQueue 无界队列
		    }*/

		
		/**当线程池被创建时，默认的情况下是没有线程产生的，除非调用了 ThreadPoolExecutor 的prestartCoreThread()方法。
		这里假设没有调用prestartCoreThread()，那么一开始的时候线程的个数为0。*/
		//ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0,0,1,null ,null);
		
		/**Starts a core thread, causing it to idly wait for work. 
		This overrides the default policy of starting core threads only when new tasks are executed. 
		This method will return false if all core threads have already been started.
		Returns:
				true if a thread was started*/
		
		//boolean prestartCoreThread = threadPoolExecutor.prestartCoreThread(); 
		/**Starts all core threads, causing them to idly wait for work. 
		This overrides the default policy of starting core threads only when new tasks are executed.
		Returns:
				the number of threads started*/
		
		//int prestartAllCoreThreads = threadPoolExecutor.prestartAllCoreThreads();
		
		
		/**在没有设置 allowCoreThreadTimeOut 为 true 的情况下，核心线程(corePoolSize 指定的个数的线程)会在线程池中一直存活，即使处于闲置状态。*/
		//threadPoolExecutor.allowCoreThreadTimeOut(true);
		
		
		/** Executors.newSingleThreadExecutor()
		 * 和上面的newFixedThreadPool类似，只不过这里只有一个线程，即使用一个线程执行任务队列中的任务。*/
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		/*public static ExecutorService newSingleThreadExecutor() {
	        return new FinalizableDelegatedExecutorService
	            (new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
	                                    new LinkedBlockingQueue<Runnable>()));//LinkedBlockingQueue 无界队列
	    }*/
		
		/**
		 * Executors.newCachedThreadPool()
		 * 创建可缓存的线程池，如果线程池中的线程在60秒未被使用就将被移除，
		       在执行新的任务时，当线程池中有之前创建的可用线程就重用可用线程，否则就新建一条线程
		
			可以看出创建了一个核心线程数为0，最大线程数为一个Integer能表示的最大值，存活时间为60秒。
			队列为SynchronousQueue直接队列。这种情况下每来一个任务就创建一个线程执行。
			直到创建的线程数大于Integer.MAX_VALUE当然一般情况下不会出现。
			每一个线程的存货时间是60秒,超过60秒后自动销毁。*/
		Executor newCachedThreadPool = Executors.newCachedThreadPool();
		 /***public static ExecutorService newCachedThreadPool() {
		        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
		                  new SynchronousQueue<Runnable>());
		                  //特殊的一个队列(直接队列)，是一种无缓冲的等待队列，可以说容量为0，是无界队列
		                  //某次添加元素后必须等待其他线程取走后才能继续添加；
		                  // 可以认为SynchronousQueue是一个缓存值为1的阻塞队列，但是 isEmpty()方法永远返回是true，
		    }*/
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
		/***
		 1 
		 * public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
		        return new ScheduledThreadPoolExecutor(corePoolSize);
		    }
		 2.
		 public ScheduledThreadPoolExecutor(int corePoolSize) {
		        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue());
		    }
		 3.
		 public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                 long keepAliveTime, TimeUnit unit,
                 BlockingQueue<Runnable> workQueue) {
			this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
			Executors.defaultThreadFactory(), defaultHandler);
			}
		*/
		
	}
}