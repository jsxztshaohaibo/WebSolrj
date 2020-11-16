package cn;import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TestTransient {

	public static void main(String[] args) {
		
		UserInfo ui1 = new UserInfo("张三","zhangsan",100);
		
		System.out.println("1."+UserInfo.getAge());
		
		UserInfo ui2 = new UserInfo("李四","lisi",200);
		
		System.out.println("2."+UserInfo.getAge());
		
		
		System.out.println("ui1."+ui1.getAge());
		System.out.println("ui2."+ui2.getAge());
		
		/*String[] arr = new String[10];
		arr[0]="张三";		
		arr[1]="李四";		
		arr[2]="王五";		
		//序列化
		try {  
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("arr.out"));  
            o.writeObject(arr);  
            o.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		//反序列化
        try {  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("arr.out"));  
            String[] readarr = (String[]) in.readObject();  
            for (int i = 0; i < readarr.length; i++) {
				System.out.println(readarr[i]);
			}
			 	//张三
				//李四
				//王五
				//null
				//null
				//null
				//null
				//null
				//null
				//null
        } catch (Exception e) {  
            e.printStackTrace();  
        }*/
		
		
		/**ArrayList 对象序列化的时候，ArrayList 的 元素数组是被 transient 修饰了，
		    但是仍然可以序列化与反序列化,因为ArrayList 重写了 writeObject方法.
		    从源码中可以看出，先调用java.io.ObjectOutputStream的defaultWriteObject方法，
		    进行默认的序列化操作，此时transient修饰的字段，没有被序列化。
		    接着：for循环，将数组中的元素写出，序列化。
		    而数组中的元素正是transient。 
		***/
		MyArrayList<String> l = new MyArrayList<String>(15);
		l.add("张三");
		l.add("李四");
		l.add("王五");
		l.add("冯八");

		//序列化
		try {  
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("l1.out"));  
            o.writeObject(l);  
            o.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		//反序列化
        try {  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("l1.out"));  
            MyArrayList<String> readList = (MyArrayList<String>) in.readObject();  
            for(String str:readList){
            	System.out.println(str);
            }
            /*for (int i = 0; i < readList.size(); i++) {
				System.out.println(readList.get(i));
			}*/
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		System.out.println("=======================================================");
		
		//=======================================================
		/*UserInfo userInfo = new UserInfo("张三", "123456");  
        System.out.println(userInfo);  
        try {  
            // 序列化，被设置为transient的属性没有被序列化  
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("UserInfo.out"));  
            o.writeObject(userInfo);  
            o.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            // 重新读取内容  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("UserInfo.out"));  
            UserInfo readUserInfo = (UserInfo) in.readObject();  
            //读取后psw的内容为null  
            System.out.println(readUserInfo.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } */

	}

}
/**
 * transient 修饰的属性的值，不会在对象序列化到磁盘文件的时候保存
 * @author Administrator
 *
 */
class UserInfo implements Serializable { 
	//final 的变量，只能在  声明    或者   通过构造函数 构造出对象   时被赋值，不会有setter方法，因为不允许被改变
    private  final String name  ;  
    private transient String psw;  
  
    private static int age ; 
    
    public static int getAge() {
		return age;
	}
	public static void setAge(int age) {
		UserInfo.age = age;
	}
	
	public UserInfo(String name, String psw) {  
        this.name = name;  
        this.psw = psw;  
    }
	public UserInfo(String name, String psw,int age ) {  
        this.name = name;  
        this.psw = psw;  
        this.age=age ;
    }
	
    public void sayHello(){
    	System.out.println("hello hello hello say three times。。。。");
    }
    public String toString() {  
        return "name=" + name + ", psw=" + psw;  
    }
    
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getName() {
		return name;
	}

}  
  