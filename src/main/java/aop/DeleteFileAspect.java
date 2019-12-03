package com.cci.safejc.commons.aop;

import com.cci.safejc.entity.FileMetadata;
import com.cci.safejc.service.SolrService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yangkai
 * @date 2019/07/24
 * @since 1.0
 */
@Aspect
@Order(1)
@Component
public class DeleteFileAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFileAspect.class);

    @Autowired
    private SolrService solrService;

    @Pointcut("@annotation(com.cci.safejc.commons.annotation.DeleteFileIndex)")
    public void handleDeleteFile() {
        LOGGER.info("Delete file, remove index from solr.");
    }

    @AfterReturning(pointcut = "handleDeleteFile()", returning = "metadata")
    public void deleteFileIndex(FileMetadata metadata) {

        if (metadata == null) {
            LOGGER.warn("%%% FileMetadata is null, failed to delete file index.");
            return;
        }

        try {
            LOGGER.info("Start delete file index for file: {}", metadata.toString());
            solrService.deleteFileIndex(metadata.getId());
            LOGGER.info("Delete file index success[{}].", metadata.toString());
        } catch (Exception e) {
            LOGGER.error(String.format("Index file error[%s]", metadata.toString()), e);
        }
    }

}
