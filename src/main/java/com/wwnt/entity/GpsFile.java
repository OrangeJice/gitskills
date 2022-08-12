package com.wwnt.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev3 on 04/01/2018.
 */
@Entity
@Table(name = "gps_file")
public class GpsFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "time")
    private Date time;
    //为单独用户所做修改
    @Column(name="user_name")
    private String userName;

    @Column(name="switch_flag")
    private Boolean switchFlag = false;

    @Column(name="select_flag")
    private int selectFlag = 0;

    @Column(name="visible_flag")
    private Boolean visibleFlag = false;

    @Column(name="dialog_flag")
    private Boolean dialogFlag = false;

    public Boolean getDialogFlag() {
        return dialogFlag;
    }

    public void setDialogFlag(Boolean dialogFlag) {
        this.dialogFlag = dialogFlag;
    }

    public Boolean getVisibleFlag() {
        return visibleFlag;
    }



    public void setVisibleFlag(Boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public Boolean getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(Boolean switchFlag) {
        this.switchFlag = switchFlag;
    }

    public int getSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(int selectFlag) {
        this.selectFlag = selectFlag;
    }

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GpsFile() {

       // = {"value":"0" ,"text":"默认"}
//        JSONObject tempMap = new JSONObject();
//        tempMap.put("value",0);
//        tempMap.put("text","默认");
//        this.selectFlag = tempMap;
//        JSON.stringify(tempMap);
    }

    @Override
    public String toString() {
        return "GpsFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", time=" + time +
                ", userName='" + userName + '\'' +
                ", switchFlag=" + switchFlag +
                ", selectFlag=" + selectFlag +
                ", visibleFlag=" + visibleFlag +
                ", dialogFlag=" + dialogFlag +
                ", format=" + format +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GpsFile(String fileName, Date time, String userName) {
        this.fileName = fileName;
        this.time = time;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTime() {
        return format.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
