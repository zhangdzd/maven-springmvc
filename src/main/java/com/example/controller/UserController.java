package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangd on 2017/5/24.
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    private UserService userSerivce;
    @Autowired
    private UserServiceImpl userServiceImp;
    @RequestMapping(value="/welcome")
    public ModelAndView getUser(ModelAndView modelView)
    {
        User user = userSerivce.getUser(8);
        System.out.println("*************************************"+user.getId()+"****"+user.getUsername()+"***"+user.getPassword());
        return new ModelAndView("welcome","user",user);
    }
    @RequestMapping(value = "findById")
    @ResponseBody
    public Map<String,Object> findById(int id){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("state","object");
        return map;
    }

    /**
     * ��ѯ
     */
    @RequestMapping("getAll")
    public String getAllUser(HttpServletRequest req,HttpServletResponse res){
       // res.setContentType("application/json;charset=UTF-8");
        List<User> list=userServiceImp.getAll();
        for (User user:list){
            System.out.print("*********************"+user);
            System.out.println(user.getId()+user.getPassword()+user.getUsername());
        }
        return "welcome";
    }

    /**
     * ����
     * @param user
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest req,HttpServletResponse res,User user){
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        userServiceImp.save(user);
        return "success";
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(HttpServletRequest req,HttpServletResponse res,User user){
        Integer id = Integer.parseInt(req.getParameter("id")==null?"":req.getParameter("id"));
        System.out.print(req.getCharacterEncoding());
        String username = req.getParameter("username")==null?"":req.getParameter("username");
        String password = req.getParameter("password")==null?"":req.getParameter("password");
        userServiceImp.update(user,id,username,password);
        return "success";
    }

    /**
     * 删除操作
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete(HttpServletRequest req,HttpServletResponse res){
        Integer id = Integer.parseInt(req.getParameter("id"));
        userServiceImp.delete(id);
        return "success";
    }
    @RequestMapping("deleteAll")
    @ResponseBody
    public String deleteAll(HttpServletRequest req,HttpServletResponse res){
        String idList = req.getParameter("id").replace("on","");
        String[] arr= idList.split(",");
        for(String a:arr){
            if(a!=null&&!"".equals(a)){
                int id = Integer.parseInt(a);
                userServiceImp.delete(id);
            }
        }
      //  Integer id =Integer.parseInt(idList.)
        return "success";
    }
}
