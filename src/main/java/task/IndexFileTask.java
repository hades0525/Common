package com.cci.safejc.commons.task;

import com.cci.safejc.service.SolrService;
import com.cci.safejc.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

/**
 * 执行文档索引重建后台定时任务。
 *
 * @author yangkai
 * @date 2019/08/09
 * @since 1.0
 */
public class IndexFileTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexFileTask.class);

    @Override
    public void run() {
        try {
            SolrService solrService = SpringContextHolder.getBean(SolrService.class);

            LOGGER.warn("Start index4CreateFailedFile");
            solrService.index4CreateFailedFile();
            LOGGER.warn("Succeed to index4CreateFailedFile.");

            LOGGER.warn("Start index4DeleteFailedFile");
            solrService.index4DeleteFailedFile();
            LOGGER.warn("Succeed to index4DeleteFailedFile");

        } catch (Exception e) {
            LOGGER.error("Index failed file error:", e);
        }
    }

}
