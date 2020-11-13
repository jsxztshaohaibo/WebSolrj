工厂模式 消除 代码里的if else 


例子中用一个   多个短信服务接口    的案例来演示


type1 

使用了工厂模式，创建了一个 SmsChannelFactory工厂类，缓存了所有的 实现短息发送业务的实现类，

如果我要增加一个短信渠道C，
	1.只需要增加一个类 SmsChannelServiceImplC 实现 SmsChannelService 接口，
	2.然后在工厂类 SmsChannelFactory 中增加一行初始化 SmsChannelServiceImplC 的代码即可。

但还是得修改原来的类 SmsChannelFactory ，不满足"开闭原则" 。


type2  

使用了 工厂模式+spring自动注入

	1.ISmsChannelService 接口增加 getChannelType() 方法，这一步很关键,实现类实现这个方法用于标识出渠道类型.

	2.实现类加上@Component 注解，使其让spring容器管理起来，

	3.SmsChannelFactory 类，加上@Component 注解使其让spring容器管理起来，

	4.通过spring将所有ISmsChannelService 接口的实现类注入到serviceList 中
			@Autowired
			private List<ISmsChannelService> serviceList;

	5.通过 @PostConstruct 注解 init()方法，在 SmsChannelFactory 实例化后，来初始化 serviceMap
	
	6.SmsSendService 加上 @Service 注解。通过 @Autowired 注入 SmsChannelFactory
	
type2模式下，新增短信渠道C的时候，只需要新增实现类就可以了，不用做其他改动
