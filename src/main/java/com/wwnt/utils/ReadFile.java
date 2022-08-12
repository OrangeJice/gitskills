package com.wwnt.utils;

import com.wwnt.entity.Gps;
import com.wwnt.entity.TrackData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReadFile.class);

    //DBConnect dbConnect=new DBConnect();
    //@Value("${MACAddress}")
    //String MACAddress;

    /*public static List<Gps> analysisFile(File file) {
        List<Gps> gpsList = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            Gps gps;
            gpsList = new ArrayList<>();
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                //System.out.println(System.lineSeparator() + s);
                try {
                    String[] str = (System.lineSeparator() + s).split(" ");
                    //String latitude = str[0].split(":")[1];
                   // String longitude = str[1].split(":")[1];
                    String latitude = str[1].split(":")[1];
                    String longitude = str[0].split(":")[1];
                    String time = str[2].split(":")[1] + " " + str[3];
                    //String cpu = str[21].split(":")[1];
                    //String model = str[14].split(":")[1];
                    time = time.replace("/", "-");
                    gps = new Gps(Double.parseDouble(latitude), Double.parseDouble(longitude), time);
                    //gps = new Gps(Double.parseDouble(latitude), Double.parseDouble(longitude), time, cpu);
                    gpsList.add(gps);
                } catch (Exception e) {
                    logger.info(e.toString());
                    continue;
                }
            }
            br.close();
        } catch (Exception e) {
            logger.info(e.toString());
        }
        logger.info("--------文件解析完毕");
        return gpsList;
    }*/
    public static TrackData analysisFile(File file) {
        TrackData trackData = null;
        List<Double> latitudeList;
        List<Double> longitudeList;
        List<String> timeList;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            latitudeList = new ArrayList<>();
            longitudeList = new ArrayList<>();
            timeList = new ArrayList<>();
            boolean firstLine = false;//判断是否是第一行
            JSONObject phoneJsonObject;
            JSONArray trackJsonArray;
            String cpu = null, mapAddress = null, model = null, board = null, hardware = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                try {
                    if (!firstLine) {
                        firstLine = true;
                        phoneJsonObject = new JSONObject();
                        cpu = phoneJsonObject.fromObject(s).getString("CPU");
                        mapAddress = phoneJsonObject.fromObject(s).getString("MACAddress");
                        model = phoneJsonObject.fromObject(s).getString("model");
                        board = phoneJsonObject.fromObject(s).getString("board");
                        hardware = phoneJsonObject.fromObject(s).getString("hardware");
                        continue;
                    }
                    trackJsonArray = new JSONArray();
                    latitudeList.add(Double.parseDouble(trackJsonArray.fromObject(s).getString(1)));
                    longitudeList.add(Double.parseDouble(trackJsonArray.fromObject(s).getString(0)));
                    if(JSONArray.fromObject(s).size() > 2){
                        //视情况判断，有的数据没有时间字段
                        timeList.add(trackJsonArray.fromObject(s).getString(2).replace("-","/"));
                    }
                } catch (Exception e) {
                    logger.info(e.toString());
                    continue;
                }
            }
            br.close();
            trackData = new TrackData(latitudeList, longitudeList, timeList, cpu, mapAddress, model, board, hardware);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        logger.info("--------文件解析完毕");
        return trackData;
    }
}
