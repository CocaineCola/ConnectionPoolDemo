package com.threadpool.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: demo
 * @description: 默认线程池实现
 * @author: quintin
 * @create: 2018-09-18 08:57
 **/
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    // 线程池最大的数量
    private static final int MAX_WORKER_NUM = 10;
    // 线程池默认的数量
    private static final int DEFAULT_WORKER_NUM = 5;
    // 线程池最小的数量
    private static final int MIN_WORKER_NUM = 1;
    // 工作列表
    private final LinkedList<Job> jobs = new LinkedList<>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    // 工作者线程数量
    private int workerNum = DEFAULT_WORKER_NUM;
    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUM);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUM ? MAX_WORKER_NUM : num < MIN_WORKER_NUM ? MIN_WORKER_NUM : num;
        initializeWorkers(workerNum);
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if(job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(x -> x.shutdown());
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if(num + this.workerNum > MAX_WORKER_NUM) {
                num = MAX_WORKER_NUM - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if(num >= this.workerNum) {
                throw new IllegalArgumentException("");
            }

            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if(workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }

            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * @program: demo
     * @description: 工作者，负责消费任务
     * @author: quintin
     * @create: 2018-09-18 09:00
     **/
    class Worker implements Runnable {

        //是否工作
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job;
                synchronized (jobs) {

                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }

                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {

                    }
                }
            }
        }

        void shutdown() {
            running = false;
        }
    }
}
