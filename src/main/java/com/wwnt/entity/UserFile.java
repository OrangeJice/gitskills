package com.wwnt.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class UserFile {
    private int flag=1;

    public UserFile() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
