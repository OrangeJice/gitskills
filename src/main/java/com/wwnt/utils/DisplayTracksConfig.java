package com.wwnt.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by dev3 on 29/12/2017.
 */
@Component
@ConfigurationProperties(prefix = "config")
@PropertySource("classpath:config.properties")
public class DisplayTracksConfig {
    private String keys;
    private String fileNameOfTrackData;
    private String filePathOfTrackData;
    private long maxFileSize;
    public String getKeys() {
        return keys;
    }
    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getFileNameOfTrackData() {
        return fileNameOfTrackData;
    }

    public void setFileNameOfTrackData(String fileNameOfTrackData) {
        this.fileNameOfTrackData = fileNameOfTrackData;
    }

    public String getFilePathOfTrackData() {
        return filePathOfTrackData;
    }

    public void setFilePathOfTrackData(String filePathOfTrackData) {
        this.filePathOfTrackData = filePathOfTrackData;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
