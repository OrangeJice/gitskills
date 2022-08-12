package com.wwnt.service;

import com.wwnt.entity.Gps;
import com.wwnt.entity.TrackData;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by dev3 on 10/01/2018.
 */
public interface DisplayTracksService {
    TrackData getTrackData(HttpSession session, String fileName);
    void deleteGcj02ListFromCache(String fileName);
}
