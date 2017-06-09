package com.example.dao;

import com.example.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by zhangd on 2017/5/24.
 */
public interface UserRepository extends JpaRepository<User,Integer> {


    @Query(value = " from User t where  t.username like ?1 order by t.id asc")
    List<User> findByIdOrUsername(String sSearch);
    @Query(value = " from User t order by t.id desc")
    List<User> getAllOrder();
}
