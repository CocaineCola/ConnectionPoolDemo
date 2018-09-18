package com.threadpool.demo;

/**
 * @program: demo
 * @description: 线程池接口
 * @author: quintin
 * @create: 2018-09-18 08:54
 **/
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorkers(int num);

    int getJobSize();

}
