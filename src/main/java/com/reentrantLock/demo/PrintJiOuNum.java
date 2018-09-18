package com.reentrantLock.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: demo
 * @description: 交替打印奇偶数
 * @author: quintin
 * @create: 2018-09-18 11:13
 **/
public class PrintJiOuNum {
    private int start = 1;
    private boolean flag = false;
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        PrintJiOuNum printJiOuNum = new PrintJiOuNum();
        printJiOuNum.flag = true;
        Thread jiNum = new Thread(new JiNum(printJiOuNum));
        Thread ouNum = new Thread(new OuNum(printJiOuNum));
        jiNum.start();
        ouNum.start();

    }

    /**
     * @program: demo
     * @description: 奇数打印
     * @author: quintin
     * @create: 2018-09-18 11:15
     **/
    static class JiNum implements Runnable {

        private PrintJiOuNum printJiOuNum;

        public JiNum(PrintJiOuNum printJiOuNum) {
            this.printJiOuNum = printJiOuNum;
        }

        @Override
        public void run() {
            while (printJiOuNum.start < 100) {

                if(printJiOuNum.flag) {
                    try {
                        lock.lock();
                        System.out.println("打印奇数：" + printJiOuNum.start++);
                        printJiOuNum.flag = false;
                    } finally {
                        lock.unlock();
                    }

                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @program: demo
     * @description: 偶数打印
     * @author: quintin
     * @create: 2018-09-18 11:15
     **/
    static class OuNum implements Runnable {

        private PrintJiOuNum printJiOuNum;

        public OuNum(PrintJiOuNum printJiOuNum) {
            this.printJiOuNum = printJiOuNum;
        }

        @Override
        public void run() {
            while (printJiOuNum.start <= 100) {

                if(!printJiOuNum.flag) {
                    try {
                        lock.lock();
                        System.out.println("打印偶数：" + printJiOuNum.start++);
                        printJiOuNum.flag = true;
                    } finally {
                        lock.unlock();
                    }

                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
