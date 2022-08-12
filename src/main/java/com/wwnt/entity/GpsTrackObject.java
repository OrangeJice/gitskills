/*
package com.wwnt.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * Created by dev3 on 28/12/2017.
 * 轨迹数据实体类 坐标未转化前
 *//*

@Entity
@Table(name = "before_correction_data")
public class GpsTrackObject {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "model")
    private String model;
    @Column(name = "cpu")
    private String cpu;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "time")
    private Date time;
    @Column(name = "mac_address")
    private String macAddress;
    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GpsTrackObject() {
    }

    public GpsTrackObject(String model, String cpu, double latitude, double longitude, Date time, String macAddress) {
        this.model = model;
        this.cpu = cpu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.macAddress = macAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return format.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

}
*/
