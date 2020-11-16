
ConcurrentLinkedQueue、AraayBlockingQueue、LinkedBlockingQueue 区别及使用场景
联系，三者 都是线程安全的。

区别，就是 并发 和 阻塞，

前者为并发队列，因为采用cas算法，所以能够高并发的处理；
后2者采用锁机制，所以是阻塞的。
注意点就是前者由于采用cas算法，虽然能高并发，
但cas的特点造成操作的危险性，怎么危险性可以去查一下cas算法


后2者区别：
联系，第2和第3都是阻塞队列，都是采用锁，都有阻塞容器Condition，
通过Condition阻塞容量为空时的取操作和容量满时的写操作第。

区别，第2就一个整锁，第3是两个锁，所以第2第3的锁机制不一样，第3比第2吞吐量 大，并发性能也比第2高。
 
后2者的具体信息: LinkedBlockingQueue是BlockingQueue的一种使用Link List的实现，

它对头和尾（取和添加操作）采用两把不同的锁，相对于ArrayBlockingQueue提高了吞吐量。

它也是一种阻塞型的容器，适合于实现“消费者生产者”模式。 

ArrayBlockingQueue是对BlockingQueue的一个数组实现，

它使用一把全局的锁并行对queue的读写操作，同时使用两个Condition阻塞容量为空时的取操作和容量满时的写操作。


正因为LinkedBlockingQueue使用两个独立的锁控制数据同步，所以可以使存取两种操作并行执行，从而提高并发效率。
而ArrayBlockingQueue使用一把锁，造成在存取两种操作争抢一把锁，而使得性能相对低下。
LinkedBlockingQueue可以不设置队列容量，默认为Integer.MAX_VALUE.其容易造成内存溢出，一般要设置其值。

使用场景：
适用阻塞队列的好处：多线程操作共同的队列时不需要额外的同步，
另外就是队列会自动平衡负载，即那边（生产与消费两边）处理快了就会被阻塞掉，
从而减少两边的处理速度差距，自动平衡负载这个特性就造成它能被用于多生产者队列，
因为你生成多了（队列满了）你就要阻塞等着，直到消费者消费使队列不满你才可以继续生产。
 当许多线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。 
LinkedBlockingQueue 多用于任务队列（单线程发布任务，任务满了就停止等待阻塞，当任务被完成消费少了又开始负载 发布任务） 
ConcurrentLinkedQueue 多用于消息队列（多个线程发送消息，先随便发来，不计并发的-cas特点）

多个生产者，对于LBQ性能还算可以接受；
但是多个消费者就不行了mainLoop需要一个timeout的机制，否则空转，cpu会飙升的。
LBQ正好提供了timeout的接口，更方便使用 如果CLQ，那么我需要收到处理sleep

总结
单生产者，单消费者 用 LinkedBlockingqueue 
多生产者，单消费者 用 LinkedBlockingqueue 

单生产者 ，多消费者 用 ConcurrentLinkedQueue 
多生产者 ，多消费者 用 ConcurrentLinkedQueue
 
对上边总结： 

如消息队列，好多client发来消息，根据client发送先后放入队列中，
先发送的就先放进来，然后由于队列是先进先出，是一个一个出来的，
所以不涉及到线程安全问题，所以用LinkedBlockingqueue 队列。

比如还拿上边消息队列那个例子，由于队列是一个一个出来的，出来一个消息协议体就由线程池分配一个线程去处理这个消息体，
这个消息体对于线程池来说谈不上共享不共享的问题，即不会多个线程去抢同一个消息体去执行，所以就不需要用线程安全的队列结构了；

那假如一种情况，队列里仍然是一个一个的出来，但是出来的这个元素是 线程池共享的，
即大家线程都需要用到这个从队列里出来的这个元素，也就是多消费者消费同一个东西这种情况，
所以就要用线程安全的队列了，即ConcurrentLinkedQueue。



有时候我们把并发包下面的所有容器都习惯叫作并发容器，但是严格来讲，只有以“Concurrent”为前缀的容器才是真正的并发容器。
ConcurrentLinkedQueue基于lock-free，在常见的多线程访问场景，一般可以提供较高吞吐量。
而LinkedBlockingQueue内部则是基于锁，并提供了BlockingQueue的等待性方法。
不知道你有没有注意到，java.util.concurrent包提供的容器从命名上可以大概分为Concurrent、CopyOnWrite和Blocking三类，
同样是线程安全容器，可以简单认为：

Concurrent类型容器没有CopyOnWrite之类容器相对较重的修改开销。
但是，凡事都是有代价的，Concurrent往往提供了较低的遍历一致性。
或称之为“弱一致性”，例如，当利用迭代器遍历时，如果容器发生修改，迭代器仍然可以继续进行遍历。

与弱一致性对应的，就是其它同步容器常见的行为“fast-fail”，
也就是检测到容器在遍历过程中发生了修改，
则抛出ConcurrentModificationException，不再继续遍历。

弱一致性的另外一个体现是，size等操作准确性是有限的，未必是百分百准确。
与此同时，读取的性能具有一定的不确定性。


体系结构
interface  Queue
		interface  BlockingQueue
				 class SynchronousQueue      extends AbstractQueue
				 class LinkedBlockingQueue   extends AbstractQueue
				 class ArrayBlockingQueue    extends AbstractQueue
				 class PriorityBlockingQueue extends AbstractQueue
		class  ConcurrnetLinkedQueue
		


SynchronousQueue  【无界的】，是一种【无缓冲】的【等待队列】，
		
		但是由于该Queue本身的特性，在某次添加元素后必须等待其他线程取走后才能继续添加；
		
		可以认为SynchronousQueue是一个缓存值为1的阻塞队列，
		
		但是 isEmpty()方法永远返回是true，
		remainingCapacity() 方法永远返回是0，
		remove()和removeAll() 方法永远返回是false，
		iterator()方法永远返回空，
		peek()方法永远返回null。
		
		声明一个SynchronousQueue有两种不同的方式，它们之间有着不太一样的行为。
		
		公平模式和非公平模式的区别:
		
		用公平模式：SynchronousQueue会采用公平锁，并配合一个FIFO队列来阻塞多余的生产者和消费者，从而体系整体的公平策略；
		
		非公平模式（SynchronousQueue默认）：SynchronousQueue采用非公平锁，同时配合一个LIFO队列来管理多余的生产者和消费者，
		而后一种模式，如果生产者和消费者的处理速度有差距，则很容易出现饥渴的情况，即可能有某些生产者或者是消费者的数据永远都得不到处理。

LinkedBlockingQueue  【无界的】，是一个【无界缓存】的【等待队列】。
		
		基于【链表】的阻塞队列，内部维持着一个数据缓冲队列（该队列由链表构成）。
		
		当生产者往队列中放入一个数据时，队列会从生产者手中获取数据，并缓存在队列内部，而生产者立即返回；
		
		只有当队列缓冲区达到最大值缓存容量时（LinkedBlockingQueue可以通过构造函数指定该值），才会阻塞生产者队列，
		
		直到消费者从队列中消费掉一份数据，生产者线程会被唤醒，反之对于消费者这端的处理也基于同样的原理。
		
		LinkedBlockingQueue之所以能够高效的处理并发数据，还因为其对于生产者端和消费者端分别采用了独立的锁来控制数据同步，
		
		这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。

ArrayBlockingQueue 【有界的】，是一个【有界缓存】的【等待队列】。

		基于【数组】的阻塞队列，同LinkedBlockingQueue类似，内部维持着一个定长数据缓冲队列（该队列由数组构成）。
		
		ArrayBlockingQueue内部还保存着两个整形变量，分别标识着队列的头部和尾部在数组中的位置。
		
		ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象，
		
		由此也意味着两者无法真正并行运行，这点尤其不同于LinkedBlockingQueue；
		
		按照实现原理来分析，ArrayBlockingQueue完全可以采用分离锁，从而实现生产者和消费者操作的完全并行运行。
		
		Doug Lea之所以没这样去做，也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，以至于引入独立的锁机制，
		
		除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。
		
		 ArrayBlockingQueue和LinkedBlockingQueue间还有一个明显的不同之处在于，
		 前者在插入或删除元素时不会产生或销毁任何额外的对象实例，
		 而后者则会生成一个额外的Node对象。
		 
		 这在长时间内需要高效并发地处理大批量数据的系统中，其对于GC的影响还是存在一定的区别。
		ArrayBlockingQueue和LinkedBlockingQueue是两个最普通、最常用的阻塞队列，
		一般情况下，处理多线程间的生产者消费者问题，使用这两个类足以。