package com.tlz.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;


/**
 * @author tenglinzhi886@gmail.com
 * @date 2019/12/16 15:08
 */

@Component
public class BeanConfig {
 
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //我这里设置的线程数是2,可以根据需求调整
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }
}