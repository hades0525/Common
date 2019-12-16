package com.tlz.cn.scheduler;

/**
 * @author tenglinzhi886@gmail.com
 * @date 2019/12/16 15:38
 */
public class TaskRunner implements Runnable{


    private static int count = 0;

    @Override
    public void run() {
        count++;
        System.out.println("Running...task1 - count : " + count);
    }
}
