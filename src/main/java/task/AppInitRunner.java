package com.cci.safejc.commons.runner;

import com.cci.safejc.commons.task.IndexFileTask;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 应用初始化操作类。
 *
 * @author yangkai
 * @date 2019/08/09
 * @since 1.0
 */
@Component
@Order(1)
public class AppInitRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitRunner.class);

    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Value("${indexTaskStartTime}")
	//04:00:00
    private String indexTaskStartTime;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("start init app...");
        try {

            this.startFileIndexTask();

        } catch (Exception e) {
            LOGGER.warn("Init app failed:", e);
        }

        LOGGER.info("init app finished.");

    }

    private void startFileIndexTask() {

        LOGGER.info("Start schedule index file task...");
        try {
            long now = System.currentTimeMillis();

            String ymd = DateFormatUtils.format(now, "yyyyMMdd");
            String datetime = String.format("%s %s", ymd, indexTaskStartTime);
            long time = DateUtils.parseDate(datetime, "yyyyMMdd HH:mm:ss").getTime();

            long diff = time - now;
            long initialDelay = 0;
            if (diff >= 0) {
                initialDelay = diff;
            } else {
                initialDelay = diff + DateUtils.MILLIS_PER_DAY;
            }

            service.scheduleAtFixedRate(new IndexFileTask(), initialDelay, DateUtils.MILLIS_PER_DAY, TimeUnit.MILLISECONDS);

            LOGGER.info("Succeed schedule index file task after {} second.", initialDelay / 1000);
        } catch (ParseException e) {
            LOGGER.error("Failed schedule index task:", e);
        }
    }
}
