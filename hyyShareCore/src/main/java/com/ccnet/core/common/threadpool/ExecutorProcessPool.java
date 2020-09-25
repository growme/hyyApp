package com.ccnet.core.common.threadpool;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 线程处理类
 */
public class ExecutorProcessPool {

    private ExecutorService executor;
    private static ExecutorProcessPool pool = new ExecutorProcessPool();
    private final int threadMax = 5000;

    private ExecutorProcessPool() {
        System.out.println("threadMax>>>>>>>" + threadMax);
        executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(threadMax);
    }

    public static ExecutorProcessPool getInstance() {
        return pool;
    }

    /**
     * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     * 
     * @author SHANHY
     * @date   2015年12月4日
     */
    public void shutdown(){
        executor.shutdown();
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     * 
     * @param task
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     * 
     * @param task
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 直接提交任务到线程池，无返回值
     * 
     * @param task
     * @author SHANHY
     * @date   2015年12月4日
     */
    public void execute(Runnable task){
    	executor.execute(task);
    }
    
    /**
     * 提交任务到线程池，可以接收线程返回值
     * 
     * @param task
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public List<Future<?>> submit(Runnable ... tasks) {
    	List<Future<?>> list = new ArrayList<Future<?>>();
    	for (Runnable runnable : tasks) {
    		list.add(this.submit(runnable));
		}
    	return list;
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     * 
     * @param task
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public <T> List<Future<T>> submit(Callable<T> ... tasks) {
    	List<Future<T>> list = new ArrayList<Future<T>>();
    	for (Callable<T> callable : tasks) {
    		list.add(this.submit(callable));
		}
      	return list;
    }

    /**
     * 直接提交任务到线程池，无返回值
     * 
     * @param task
     * @author SHANHY
     * @date   2015年12月4日
     */
    public void execute(Runnable ... tasks){
    	for (Runnable runnable : tasks) {
    		executor.execute(runnable);
		}
    }

}