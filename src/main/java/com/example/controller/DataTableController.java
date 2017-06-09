package com.example.controller;



import com.example.pojo.User;
import com.example.service.UserServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JdbcUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangd on 2017/5/31.
 */
@Controller
public class DataTableController {

    @Autowired
    private UserServiceImpl userServiceImp;
    @RequestMapping(value="tableDemoAjax",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String tableDemoAjax(@RequestParam String aoData) {
        JSONArray jsonarray = JSONArray.fromObject(aoData);
        String sEcho = null;      //????????????
        String sSearch = null;
        int iDisplayStart = 0;    // ???????
        int iDisplayLength = 0;   // ??????????
        List<User> lst = new ArrayList<User>();


        //???????????????????????????????????????????????????????
        for (int i = 0; i < jsonarray.size(); i++ ) {
            JSONObject obj = (JSONObject) jsonarray.get(i);
            if (obj.get("name").equals("sEcho"))
                sEcho = obj.get("value").toString();
            if (obj.get("name").equals("iDisplayStart"))
                iDisplayStart = obj.getInt("value");
            if (obj.get("name").equals("iDisplayLength"))
                iDisplayLength = obj.getInt("value");
            if(obj.get("name").equals("sSearch"))
               // sSearch = obj.get("sSearch")==null?"":obj.get("sSearch").toString();
                sSearch = obj.getString("value");
            System.out.print("sSearch="+sSearch);
        }

        /*// ????20?????????????????????????????????????????????????????????????????????
        List<String[]> lst = new ArrayList<String[]>();
        for (int i = 0; i < 100; i++) {
            String[] d = { "firstname"+i,"lastname"+i,"address"+i };
            lst.add(d);
        }
*/

        if(sSearch.equals("")){
            lst = userServiceImp.getAllOrder();
        }else{
            //  String sql = "select * from i_user2 where username like '%"+sSearch+"%' or password like '%"+sSearch+"%'";
            //  List<User> lst = new ArrayList<User>();
            lst = userServiceImp.findByIdOrUsername(sSearch);
        }
        for (User user:lst){
            System.out.print("*********************"+user);
            System.out.println(user.getId()+user.getPassword()+user.getUsername());
        }
        JSONObject getObj = new JSONObject();
        System.out.println("sEcho:"+sEcho);
        getObj.put("sEcho", sEcho);
        getObj.put("iTotalRecords", lst.size());          //????????
        getObj.put("iTotalDisplayRecords", lst.size());   //?????????,????????????????
        System.out.print("lst.size="+lst.size());
        if(iDisplayStart>=lst.size()/iDisplayLength*iDisplayLength){
            getObj.put("aaData", lst.subList(iDisplayStart,lst.size()));//???JSON????????????????????
        }else{
            getObj.put("aaData", lst.subList(iDisplayStart,iDisplayStart+iDisplayLength));//???JSON????????????????????
        }
      //  getObj.put("aaData", lst);
        return getObj.toString();
    }
}
