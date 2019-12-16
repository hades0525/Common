package com.tlz.cn.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author tenglinzhi886@gmail.com
 * @date 2019/12/16 15:08
 */

public class ScheduledExecutorRepeat {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {


        ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder()
        .setNameFormat("demo-pool-%d").setDaemon(true).build(), new ThreadPoolExecutor.AbortPolicy());


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
                10,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());


        Runnable task1 = () -> {
            count++;
            System.out.println("Running...task1 - count : " + count);
        };

        // init Delay = 5, repeat the task every 1 second
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 5, 1, TimeUnit.SECONDS);

        while (true) {
            System.out.println("count :" + count);
            Thread.sleep(1000);
            if (count == 5) {
                System.out.println("Count is 5, cancel the scheduledFuture!");
                scheduledFuture.cancel(true);
                ses.shutdown();
                break;
            }
        }




    }
}