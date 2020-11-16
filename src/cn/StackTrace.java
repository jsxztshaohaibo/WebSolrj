package cn;



public class StackTrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String str = "java:comp/env/aeers/OracleDS";
		int colon_posn = str.indexOf(':');
	    int slash_posn = str.indexOf('/');

	    if (colon_posn > 0 && (slash_posn == -1 || colon_posn < slash_posn))
	        System.out.println( str.substring(0, colon_posn));
		
		
		/*// TODO Auto-generated method stub
		printCallStatck();
		getStackTraceByCurrentThread();*/
		
	}
	public static void printCallStatck() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
            	System.out.print(stackElements[i]+"\t\t");
                System.out.print(stackElements[i].getClassName()+"\t");
                System.out.print(stackElements[i].getFileName()+"\t");
                System.out.print(stackElements[i].getLineNumber()+"\t");
                System.out.println(stackElements[i].getMethodName());
                System.out.println("-----------------------------------");
            }
        }
        
        System.out.println("===========================");
    }
	
	public static void   getStackTraceByCurrentThread() {
		
		 StackTraceElement[] stackTraceArr = Thread.currentThread().getStackTrace() ;
		 System.out.println(stackTraceArr.length);
		 for(StackTraceElement st : stackTraceArr){
			 System.out.println(st);
		 }
		
		 
	}
	public static void runtimeTest(){
		/*System.out.println(Runtime.class.getClassLoader());//null
		System.gc();// ====>public static void gc() {
					// ====>	Runtime.getRuntime().gc();
					// ====>}
		System.load("");// ====>public static void load(String filename) {
					    // ====>	Runtime.getRuntime().load0(getCallerClass(), filename);
					    // ====>}
		Runtime.getRuntime().load(""); // ====>public void load(String filename) {
									   // ====>	load0(System.getCallerClass(), filename);
									   // ====>}
		 */
		long maxMemory = Runtime.getRuntime().maxMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		System.out.println("maxMemory 最大可用内存大小(KB)"+maxMemory/1/1/1024d);
		System.out.println("totalMemory 总内存大小(KB)"+totalMemory/1/1/1024d);
		System.out.println("freeMemory 可用内存大小(KB)"+freeMemory/1/1/1024d);
	}
	
    
}
