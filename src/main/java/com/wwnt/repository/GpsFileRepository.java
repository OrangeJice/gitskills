package com.wwnt.repository;

import com.wwnt.entity.GpsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dev3 on 04/01/2018.
 */
public interface GpsFileRepository extends JpaRepository<GpsFile, Long> {


    /**
     * 拿file列表
     *
     * @param username 用户名
     * @return
     */
    @Query(value = "SELECT * from gps_file where user_name = ?1", nativeQuery = true)
    List<GpsFile> getFileList(String username);


    /**
     * postgresql分页
     *
     * @param row   每页显示条数
     * @param start 起始下标
     * @return
     */
    //@Query(value = "SELECT * from gps_file where user_name = ?3 ORDER BY id LIMIT ?1 OFFSET ?2", nativeQuery = true)
    //List<GpsFile> getPageFileList(int row, int start,String username);

    /**
     * 文件总条数
     *
     * @return
     */
    //@Query(value = "SELECT count(*) from gps_file where user_name=?1 ", nativeQuery = true)
    //int getRowCount(String username);

    /**
     * 通过文件名分页查询
     * @param fileName 文件名
     * @param row 查询条数
     * @param start 起始位置
     * @return
     */
    //@Query(value = "SELECT * from gps_file WHERE file_name LIKE %?1% and user_name=?4 ORDER BY id LIMIT ?2 OFFSET ?3",nativeQuery = true)
    //List<GpsFile> getFileListByFileName(String fileName,int row, int start,String username);

    /**
     *通过时间分页查询
     * @param time 时间
     * @param row 查询条数
     * @param start 起始位置
     * @return
     */
    //@Query(value = "SELECT * FROM gps_file WHERE to_char(time,'YYYY-MM-DD HH24:mi:ss') and user_name=?4 LIKE %?1% ORDER BY id LIMIT ?2 OFFSET ?3", nativeQuery = true)
    //List<GpsFile> getFileListByTime(String time,int row, int start,String username);

    /**
     * 该文件名有关的数据条数
     * @param fileName
     * @return
     */
    //@Query(value = "SELECT count(*) from gps_file WHERE user_name=?2 and file_name LIKE %?1%",nativeQuery = true)
    //int getCountByFileName(String fileName,String username);

    /**
     * 该时间有关的数据条数
     * @param time
     * @return
     */
    //@Query(value = "SELECT count(*) FROM gps_file WHERE user_name=?2 and to_char(time,'YYYY-MM-DD HH24:mi:ss') LIKE %?1%",nativeQuery = true)
    //int getCountByTime(String time,String username);
    /**
     *
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "delete from gps_file where id = ?1 and user_name = ?2",nativeQuery = true)
    int deleteFileById(int id,String username);

    @Transactional
    @Modifying
    @Query(value = "delete from gps_file where user_name = ?1",nativeQuery = true)
    int deleteUser(String username);
    /*
    找出username用户的轨迹
    */
    @Query(value = "SELECT file_name FROM gps_file WHERE user_name = ?1 ",nativeQuery = true)
    List<String> selectTracksNameByUsername(String username);
    /*
    * 更新filename
    *
    * */
    @Modifying
    @Transactional
    @Query(value = "update gps_file set file_name=?1 WHERE id = ?2 ",nativeQuery = true)
    void updateFileName(String file_name,Integer id);
}
