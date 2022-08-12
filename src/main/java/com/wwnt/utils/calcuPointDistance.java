package com.wwnt.utils;

import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 高德地图工具
 *
 * @author jianggujin
 *
 */
public class calcuPointDistance
{
    /**
     * 根据用户的起点和终点经纬度计算两点间距离，此距离为相对较短的距离，单位米。
     *
     * @param x1,y1
     *           起点的坐标
     * @param x2,y2
     *           终点的坐标
     * @return
     */
    public static double calculateLineDistance(double x1,double y1, double x2,double y2)
    {

        double d1 = 0.01745329251994329D;
        double d2 = x1;
        double d3 = y1;
        double d4 = x2;
        double d5 = y2;
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
//        for(int i=0;i<=100;i++){
//            String s = String.valueOf(i);
//            String s1 =  DigestUtils.md5DigestAsHex(s.getBytes(StandardCharsets.UTF_8));
//            String s2 = "INSERT INTO public.user_pro(org_user_name, pass_word, user_name)VALUES ("+ i+",'"+s1+"' ,'"+s1 +"');";
//            System.out.println(s2);
//        }
        //int j=244;
        //for(int i=0;i<=100;i++){
         //   String s = String.valueOf(i);
            //String s1 =  DigestUtils.md5DigStringestAsHex(s.getBytes(StandardCharsets.UTF_8));
            //String s2 = "副本";
            //s2 = URLEncoder.encode(s2,"utf-8");
            //String s3 = "D:\\MyResources\\file\\gpsData\\81dc9bdb52d04dc20036dbd8313ed055\\11-09 - "+s2+" ("+s+").txt";
            //System.out.println(s3);
            //File file1 = new File(s3);
            //File file2 = new File("D:\\MyResources\\file\\gpsData\\81dc9bdb52d04dc20036dbd8313ed055\\11-09 - ("+s+").txt");
            //System.out.println(file1.renameTo(file2));
            //System.out.println(j +",11-09-("+s+").txt");
            //j++;
       // }
//        File file1 = new File("D:\\MyResources\\file\\gpsData\\81dc9bdb52d04dc20036dbd8313ed055");
//        File[] files = file1.listFiles();
//        for(File file:files){
//
//            System.out.println(file.getName());
//        }
    for(int i=619;i<=719;i++){
        System.out.println(i);
    }
    }
}
