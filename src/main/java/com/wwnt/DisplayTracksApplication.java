package com.wwnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * add comments
 */
@SpringBootApplication
public class DisplayTracksApplication extends SpringBootServletInitializer {


        public static void main (String[]args) {
            SpringApplication.run(DisplayTracksApplication.class, args);
        }


        @Override
        protected SpringApplicationBuilder configure (SpringApplicationBuilder builder){

            return builder.sources(DisplayTracksApplication.class);
        }
    }
