/*
package com.wwnt.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * Created by dev3 on 28/12/2017.
 * 轨迹数据实体类 对应坐标为高德坐标
 *//*

@Entity
@Table(name = "gps_to_gcj")
public class GcjTrackObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cpu")
    private String cpu;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "time")
    private Date time;

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GcjTrackObject() {
    }

    public GcjTrackObject(String cpu, String latitude, String longitude, Date time) {
        this.cpu = cpu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return format.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
*/
