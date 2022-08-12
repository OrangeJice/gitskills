package com.wwnt.repository;

import com.wwnt.entity.GpsFile;
import com.wwnt.entity.postionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev3 on 04/01/2018.
 */
public interface PointRepository extends JpaRepository<postionPoint, Integer> {
    /**
     * 查询所有
     * @return ArratList
     *
     * */
    @Query(value = "select * from point_table", nativeQuery = true)
    ArrayList list();

    /**
     * 查询所有 fileName
     * @return ArratList
     *
     * */
    @Query(value = "select distinct file_name from point_table ", nativeQuery = true)
    ArrayList<String> selectfileName();

    /**
     * 查询文件名是fileName的
     * @return ArratList
     *
     * */
    @Query(value = "select x,y,file_name,time,speed from point_table where file_name = ?1", nativeQuery = true)
    ArrayList selectByfileName(String fileName);

    /**
     * 按照id更新速度
     * @param speed 速度
     * @param id id
     * @return 受影响行数
     * */
    @Transactional
    @Modifying
    @Query(value = "update point_table set speed = ?1 where id = ?2", nativeQuery = true)
    void updateById(double speed,Integer id);

    /**
     * 选出最大的id
     * @return 最大的id
     * */
    @Query(value = "select max (id) from point_table ", nativeQuery = true)
    int selsectMaxId();

    /**
     * @param fileName 文件名
     * @return 某一文件所有速度
     * */
    @Query(value = "select speed from point_table where file_name = ?1", nativeQuery = true)
    String[] selsectSpeedByFileName(String fileName);

    /**
     * @param fileName 文件名
     * @return 某一文件所有速度
     * */
    @Transactional
    @Modifying
    @Query(value = "delete from point_table where file_name = ?1", nativeQuery = true)
    void deleteByFileName(String fileName);

    /**
     * @param fileName 文件名
     * @return x,y
     * */
    //@Query(value = "select x,y from point_table where file_name = ?1", nativeQuery = true)
    //ArrayList selectXYByFileName(String fileName);

    /**
     * 根据文件名改名
     * @param fileName 文件名
     * @return x,y
     * */

    @Transactional
    @Modifying
    @Query(value = "update point_table set file_name = ?2 where file_name = ?1", nativeQuery = true)
    int updateFileNameByFileName(String fileName,String newFileName);
}
