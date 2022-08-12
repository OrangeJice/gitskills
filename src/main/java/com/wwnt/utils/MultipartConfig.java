package com.wwnt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by dev3 on 04/01/2018.
 */
@Configuration
public class MultipartConfig {
    private static final Logger logger = LoggerFactory.getLogger(MultipartConfig.class);
    @Autowired
    DisplayTracksConfig displayTracksConfig;
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(displayTracksConfig.getMaxFileSize());
        logger.info("--------上传的文件最大为："+displayTracksConfig.getMaxFileSize());
        return factory.createMultipartConfig();
    }
}
