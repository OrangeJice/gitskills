package com.wwnt.entity;

import com.wwnt.utils.GetLocations;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dev3 on 29/12/2017.
 */
//@Component
//@Lazy(true)//延迟加载，用到才加载
public class DisplayTracksAsyncTask {
   /* @Autowired
    GetLocations getLocations;*/

    //@Async("displayTracksAsyncAsync")
    /*public JSONArray getdisplayTracks(String filePathName) {
        List<Gps> gcj02List = getLocations.getGcj02List(filePathName);
        JSONArray pointArray = new JSONArray();
        String[] pathArray = new String[gcj02List.size()];
        JSONObject routeObject = new JSONObject();
        routeObject.put("time", gcj02List.get(0).getTime());
        routeObject.put("cpu", gcj02List.get(0).getCpu());
        StringBuffer stringBuffer;
        for (int i = 0; i < gcj02List.size(); i++) {
            stringBuffer = new StringBuffer("[");
            stringBuffer.append(gcj02List.get(i).getWgLon());
            stringBuffer.append(",");
            stringBuffer.append(gcj02List.get(i).getWgLat());
            stringBuffer.append("]");
            pathArray[i] = stringBuffer.toString();
            //pathArray[i] = "[" + gcj02List.get(i).getWgLon() + "," + gcj02List.get(i).getWgLat() + "]";
        }
        routeObject.put("path", pathArray);
        pointArray.add(routeObject);
        //System.out.println(pointArray.toString());
        return pointArray;
    }*/
}
