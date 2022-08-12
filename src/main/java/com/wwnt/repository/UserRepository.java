package com.wwnt.repository;

import com.wwnt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jca.cci.core.InteractionCallback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
    *  返回user表的行数
    * */
    @Query(value = "SELECT count(*) from user_pro", nativeQuery = true)
    int getRowCount();

    /**
     *  返回user表最大id 用于测试删除
     * */
    @Query(value = "SELECT max(id) from user_pro", nativeQuery = true)
    int selectMaxId();

    /**
     * 返回姓名是username的行数(查看用户名是否存在)
     * @param  username user's name
     *
     * */
    @Query(value = "select count(*) from user_pro where user_name = ?1", nativeQuery = true)
    int selectByusername(String username);


    /**
     * 按用户名密码查找用户
     * @param username 用户名
     * @param password 密码
     * @return User
     * */
    @Query(value = "select * from user_pro where user_name = ?1 and pass_word = ?2",nativeQuery = true)
    User selectByUsernameandPassword(String username, String password);

    /**
     * 查看user表
     * @return List<User>
     *
     * */
    @Query(value = "select * from user_pro",nativeQuery = true)
    List<User> list();

    /**
     * 根据id删除用户
     * @param id
     * @return int 受影响行数
     * */
    @Transactional
    @Modifying
    @Query(value = "delete from user_pro where id = ?1",nativeQuery = true)
    int deleteUserById(Integer id);

    /**
     * 查看user表
     * @return List<User>
     *
     * */
    @Query(value = "select user_name from user_pro where id=?1",nativeQuery = true)
    String selectUserNameById(Integer id);
}
