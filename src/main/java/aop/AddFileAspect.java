package com.cci.safejc.commons.aop;

import com.cci.safejc.entity.FileMetadata;
import com.cci.safejc.service.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yangkai
 * @date 2019/07/24
 * @since 1.0
 */
@Aspect
@Order(1)
@Component
public class AddFileAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddFileAspect.class);

    @Autowired
    private SolrService solrService;

    @Pointcut("@annotation(com.cci.safejc.commons.annotation.AddFileIndex)")
    public void handleAddFile() {
        LOGGER.info("Add file, index to solr.");
    }


    @AfterReturning(pointcut = "handleAddFile()", returning = "metadata")
    public void afterReturning(FileMetadata metadata) {

        try {
            LOGGER.info("Start add file index[{}]", metadata.toString());
            solrService.indexFile(metadata);
            LOGGER.info("Add file index success[{}]", metadata.toString());

        } catch (IOException e) {
            LOGGER.error(String.format("Index file error[%s]", metadata.toString()), e);
        } catch (SolrServerException e) {
            LOGGER.error(String.format("Index file error[%s]", metadata.toString()), e);
        }
    }

}
