package com.example.service;

import com.example.dao.UserRepository;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * Created by zhangd on 2017/5/24.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;
    public UserRepository getRepository(){
        return repository;
    }
    public User getUser(int id){
        return repository.findOne(id);
    }

    /**
     * jpa ��ѯ����
     * @return
     */
    public List<User> getAll(){
        return repository.findAll();
    }

    /**
     * jpa���淽��
     */
    public void save(User user){
        repository.save(user);
    }

    /**
     * ����
     * @param user
     */
    @Transactional
    public void update(User user,Integer id,String username,String password){
        user=repository.findOne(id);
        user.setUsername(username);
        user.setPassword(password);
    }

    /**
     * ɾ��
     * @param id
     */
    public void delete(Integer id){
          repository.delete(id);
    }

    /**
     * 模糊查询
     * @param sSearch
     * @return
     */
    public  List<User> findByIdOrUsername(String sSearch){
        return repository.findByIdOrUsername(sSearch+"%");
    }

    /**
     * 排序查询
     */
    public List<User> getAllOrder(){

        return repository.getAllOrder();
    }
}
