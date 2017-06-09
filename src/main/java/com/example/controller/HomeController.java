package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Chingyu Mo
 * @create 2016-07-23-20:20
 */
// ע���ע����Ϊspringmvc��controller��urlӳ��Ϊ"/home"
@Controller
@RequestMapping("/home")
public class HomeController {
    //���һ����־��
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    //ӳ��һ��action
    @RequestMapping("/index")
    public  String index(){
        //�����־�ļ�
        logger.info("the first jsp pages");
        //����һ��index.jsp�����ͼ
        return "index";
    }
}