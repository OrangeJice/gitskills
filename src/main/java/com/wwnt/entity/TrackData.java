package com.wwnt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev3 on 11/01/2018.
 */
public class TrackData {
    private List<Double> latitudeList = new ArrayList<>();
    private List<Double> longitudeList = new ArrayList<>();
    private List<Double> formatedLatitudeList = new ArrayList<>();
    private List<Double> formatedLongitudeList = new ArrayList<>();

    @Override
    public String toString() {
        return "TrackData{" +
                "latitudeList=" + latitudeList +
                ", longitudeList=" + longitudeList +
                ", formatedLatitudeList=" + formatedLatitudeList +
                ", formatedLongitudeList=" + formatedLongitudeList +
                ", timeList=" + timeList +
                ", cpu='" + cpu + '\'' +
                ", MACAddress='" + MACAddress + '\'' +
                ", model='" + model + '\'' +
                ", board='" + board + '\'' +
                ", hardware='" + hardware + '\'' +
                '}';
    }

    private List<String> timeList = new ArrayList<>();
    private String cpu;
    private String MACAddress;
    private String model;
    private String board;
    private String hardware;

    public TrackData(List<Double> latitudeList, List<Double> longitudeList, List<String> timeList, String cpu, String MACAddress, String model, String board, String hardware) {
        this.latitudeList = latitudeList;
        this.longitudeList = longitudeList;
        this.timeList = timeList;
        this.cpu = cpu;
        this.MACAddress = MACAddress;
        this.model = model;
        this.board = board;
        this.hardware = hardware;
    }

    public List<Double> getLatitudeList() {
        return latitudeList;
    }

    public void setLatitudeList(List<Double> latitudeList) {
        this.latitudeList = latitudeList;
    }


    public List<Double> getLongitudeList() {
        return longitudeList;
    }

    public void setLongitudeList(List<Double> longitudeList) {
        this.longitudeList = longitudeList;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMACAddress() {
        return MACAddress;
    }

    public void setMACAddress(String MACAddress) {
        this.MACAddress = MACAddress;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public List<Double> getFormatedLatitudeList() {
        return formatedLatitudeList;
    }

    public void setFormatedLatitudeList(List<Double> formatedLatitudeList) {
        this.formatedLatitudeList = formatedLatitudeList;
    }

    public List<Double> getFormatedLongitudeList() {
        return formatedLongitudeList;
    }

    public void setFormatedLongitudeList(List<Double> formatedLongitudeList) {
        this.formatedLongitudeList = formatedLongitudeList;
    }
}
