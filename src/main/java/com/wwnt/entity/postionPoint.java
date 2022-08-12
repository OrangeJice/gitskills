package com.wwnt.entity;

import org.hibernate.annotations.Type;
//import org.locationtech.jts.geom.Geometry


import javax.persistence.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "point_table")
public class postionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name="file_name")
    private String fileName;

    @Column(name="user_name")
    private String userName;

    @Column(name="time")
    private Date time;

    @Column(name="speed")
    private double speed;

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public postionPoint(double x, double y, String fileName, String userName, Date time, double speed) {

        this.x = x;
        this.y = y;
        this.fileName = fileName;
        this.userName = userName;
        this.time = time;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "postionPoint{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", fileName='" + fileName + '\'' +
                ", time=" + time +
                ", speed=" + speed +
                ", format=" + format +
                '}';
    }

    public postionPoint() {
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }
}
