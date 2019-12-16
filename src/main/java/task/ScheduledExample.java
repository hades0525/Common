package com.tlz.cn.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tenglinzhi886@gmail.com
 * @date 2019/12/16 15:08
 */
@Component
public class ScheduledExample {


    /**
     * 同一任务的同步执行(下次任务执行将在本次任务执行完毕后的下一次配置时间开始)
     * @throws InterruptedException
     * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)} 6个+一个年
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void ipWriter1() throws InterruptedException {
        for(int i=0;i<20;i++){
            System.out.println("1:"+i);
            Thread.sleep(5000);
        }
    }


    /**
     * 同一任务的异步执行(下次任务将在下一个配置时间开始,不等待当前任务执行完毕)
     * @throws InterruptedException
     */
    @Async
    @Scheduled(cron = "*/30 * * * * ?")
    public void ipWriter2() throws InterruptedException {
        for(int i=0;i<20;i++){
            System.out.println("1:"+i);
            Thread.sleep(5000);
        }
    }



}
