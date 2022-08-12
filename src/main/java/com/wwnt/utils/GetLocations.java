package com.wwnt.utils;

import com.wwnt.entity.Gps;
import com.wwnt.entity.TrackData;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * gps坐标转高德坐标
 */
@Component
public class GetLocations {
    private static final Logger logger = LoggerFactory.getLogger(GetLocations.class);
    /*@Autowired
    private DisplayTracksConfig displayTracksConfig;*/

    /**
     * 调用坐标转化工具进行坐标转化
     *
     * @param filePathName
     * @return
     */
    public TrackData getTrackData(String filePathName) {
        //String filePath = displayTracksConfig.getFilePathOfTrackData();
        //String fileName = displayTracksConfig.getFileNameOfTrackData();
        File file = new File(filePathName);
        if (!file.exists()) {
            logger.info("--------文件不存在");
            return null;
        } else {
            TrackData trackData = ReadFile.analysisFile(file);//文件解析
            /**
             * 坐标转化
             */
            List<Double> latitudeList = new ArrayList<>();
            List<Double> longitudeList = new ArrayList<>();
            Gps gcj02Object;
            for (int i = 0; i < trackData.getLatitudeList().size(); i++) {
                try{
                    gcj02Object = PositionUtil.gps84_To_Gcj02(trackData.getLatitudeList().get(i), trackData.getLongitudeList().get(i));
                    latitudeList.add(gcj02Object.getWgLat());
                    longitudeList.add(gcj02Object.getWgLon());
                }catch (Exception e){
                    System.out.println(trackData.getLatitudeList().get(i).toString()+ " "+trackData.getLongitudeList().get(i).toString()+" "+trackData.getTimeList().get(i).toString());
                    continue;
                }
            }
            trackData.setFormatedLatitudeList(latitudeList);
            trackData.setFormatedLongitudeList(longitudeList);
            logger.info("--------坐标转化完成");
            return trackData;
        }
    }

    /**
     * java使用脚本引擎执行js
     * 运行JS基本函数
     */
    /*public static String jsFunction(double lng,double lat) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        String result = null;
        try {
            String script = "function gpsToGcj(lng,lat) {\n" +
                    "    AMap.convertFrom(lng+','+lat, 'gps', function(meg) {\n" +
                    "        return meg;\n" +
                    "    })\n" +
                    "}";
            //String script = "function say(name){ return 'hello,'+name; }";
            se.eval(script);
            Invocable inv2 = (Invocable) se;
            result = (String) inv2.invokeFunction("gpsToGcj",lng,lat);
            //System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

    /**
     * 高德API坐标转化
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static JSONObject gpsToGCJ_02(double longitude, double latitude) {
        //String keys = displayTracksConfig.getKeys();
        String key = "f5537e490c7bbcf82ccbcc1ef536595c";
        /**
         * 经度和纬度用","分割，经度在前，纬度在后，
         * 经纬度小数点后不得超过6位。多个坐标对之间用”|”进行分隔,最多支持40对坐标。
         * coordsys:原坐标系 可选值：gps;mapbar;baidu;autonavi(不进行转换)
         * output:返回数据格式类型 可选值：JSON,XML
         * status:返回状态;info:status为0时，info返回错误原，否则返回“OK”;locations:转换之后的坐标。若有多个坐标，则用 “;”进行区分和间隔。
         */
        String location = longitude + "," + latitude;
        String url = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + location + "&coordsys=gps&output=JSON&key=" + key;
        //发送http请求
        String result = sendGet(url);
        return JSONObject.fromObject(result);
    }

    public static String getposition(double latitude, double longitude) {
        //String address = "tcp://58.59.82.234:1883";
        String url = "https://www.navi-tech.net/MessagesCenter/getlocationdata?location=" + latitude + "," + longitude;
        String a = sendGet(url);
        JSONObject position = null;
        try {
            position = JSONObject.fromObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String location1 = null;
        try {
            location1 = position.getString("locationdata");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location1;
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
