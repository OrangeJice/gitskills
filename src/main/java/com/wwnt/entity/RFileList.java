package com.wwnt.entity;

import java.util.List;

public class RFileList {
    private Boolean flag;

    public Boolean getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "RFileList{" +
                "flag=" + flag +
                ", fileList=" + fileList +
                '}';
    }

    public RFileList() {
    }

    public RFileList(Boolean flag) {
        this.flag = flag;
    }

    public RFileList(Boolean flag, List<GpsFile> fileList) {


        this.flag = flag;
        this.fileList = fileList;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;

    }

    public List<GpsFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<GpsFile> fileList) {
        this.fileList = fileList;
    }

    private List<GpsFile> fileList;
}
