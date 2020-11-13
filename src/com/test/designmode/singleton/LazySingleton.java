package com.test.designmode.singleton;
public class LazySingleton {
 
    private static volatile LazySingleton instance = null;
     
    /**
     * 极限多线程情况下，这个方法会有问题--会创建多个实例出来
     * @return
     */
    public static LazySingleton getInstance() {
        if (instance == null) {//进入if 以后，让出时间片的情况下，会创建多个实例
            instance = new LazySingleton();
        }
        return instance;
    }
     
    public static void main(String[] args) {
        LazySingleton.getInstance();
    }
    
   /* 
    * volatile+  DOUBLE CHECK + Lock 模式  (volatile + DCL)
    private volatile static Singleton instance = null;
    private Singleton() {}
    public static Singleton getInstance() {
        if(instance==null) {
            synchronized (Singleton.class) {
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    */
}