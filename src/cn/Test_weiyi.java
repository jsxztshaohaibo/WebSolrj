package cn;import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Test_weiyi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println((1 << 32));
		
		long l = (1 << 31);
		System.out.println(l);
		System.out.println(Integer.MAX_VALUE);//2147483647
		System.out.println( (Math.pow(2, 31)));
		
		//Map<Object, Object> synchronizedMap = Collections.synchronizedMap(null);//实际返回的是 Collections 的内部类 SynchronizedMap，
		
		/**无符号位移---不知道怎么解释
		int i =-8;
		System.out.println(i>>>2);//1073741822 
		**/
		
		
		/**位运算符  
		 * 右移运算符">>n":将一个数的二进制位全部向右移n位,最右边n位舍弃，左边补0; 相当于除以 2的  N 次方
		 * 
		 * 左移运算符"<<n":将一个数的二进制位全部向左移n位,最左边n位舍弃，右边补0; 相当于乘以 2 的  N 次方   
		 **/
		/*int i =16;
		System.out.println(i>>1);// 8 -->相当于 16  除以(2*1) 2的1次方
		System.out.println(i>>2);// 4 -->相当于 16  除以(2*2) 2的2次方
		System.out.println(i>>3);// 2 -->相当于 16  除以(2*3) 2的3次方
		System.out.println(i>>4);// 1 -->相当于 16  除以(2*4) 2的4次方
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		
		int j =3;
		System.out.println(j<<1);// 6  -->相当于 3 乘以(2*1) 2的1次方
		System.out.println(j<<2);// 12 -->相当于 3 乘以(2*2) 2的2次方
		System.out.println(j<<3);// 24 -->相当于 3 乘以(2*3) 2的3次方
		System.out.println(j<<4);// 48 -->相当于 3 乘以(2*4) 2的4次方
		System.out.println("<<<<<<<<<<<<<<<<<<<");*/
		
		
		
		/**循环队列，利用iterator 删除列表中的元素()**/
		/*List<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			l.add(i);
		}
		for(Integer i :l){
			System.out.println(i);
		}
		System.out.println("=============");
		Iterator<Integer> iterator = l.iterator();
		while(iterator.hasNext()){
			Integer next = iterator.next();
			if(next%2==0){
				iterator.remove();
			}
		}
		for(Integer i :l){
			System.out.println(i);
		}*/
	}
}
