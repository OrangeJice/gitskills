package com.wwnt.service.impl;

import com.wwnt.entity.Gps;
import com.wwnt.entity.TrackData;
import com.wwnt.service.DisplayTracksService;
import com.wwnt.utils.DisplayTracksConfig;
import com.wwnt.utils.GetLocations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by dev3 on 10/01/2018.
 */
@Service
@EnableCaching
public class DisplayTracksServiceImpl implements DisplayTracksService {
    private static final Logger logger = LoggerFactory.getLogger(DisplayTracksServiceImpl.class);
    @Autowired
    GetLocations getLocations;
    @Autowired
    DisplayTracksConfig displayTracksConfig;

    @Override
    @Cacheable(value = "trackData", key = "#fileName")
    public TrackData getTrackData(HttpSession session,String fileName) {
        String username = (String)session.getAttribute("username");
//        String username = "81dc9bdb52d04dc20036dbd8313ed055";

        TrackData trackData = getLocations.getTrackData(displayTracksConfig.getFilePathOfTrackData()+"\\"+username+"\\" + fileName);
        logger.info("--------我没有走缓存");
        return trackData;
    }

    @Override
    @CacheEvict(value = "trackData", key = "#fileName")
    public void deleteGcj02ListFromCache(String fileName) {
        logger.info("------清除了名为：" + fileName + "的缓存");
    }
}
