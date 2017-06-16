package com.example.controller;



import com.example.pojo.Person;
import com.example.pojo.User;
import com.example.service.PersonServiceImpl;
import com.example.service.UserServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangd on 2017/5/31.
 */
@Controller
public class DataTableController {

    @Autowired
    private UserServiceImpl userServiceImp;
    @Autowired
    private PersonServiceImpl personService;
    @RequestMapping(value="tableDemoAjax",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String tableDemoAjax(@RequestParam String aoData,HttpServletRequest request) {
        String department = request.getParameter("nodeId");
        JSONArray jsonarray = JSONArray.fromObject(aoData);
        String sEcho = null;      //????????????
        String sSearch = null;
        int iDisplayStart = 0;    // ???????
        int iDisplayLength = 0;   // ??????????
    //    List<User> lst = new ArrayList<User>();      //查询User表
        List<Person> lst = new ArrayList<Person>();   //查询person表


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

        /*if(sSearch.equals("")){
            lst = userServiceImp.getAllOrder();
        }else{
            lst = userServiceImp.findByIdOrUsername(sSearch);
        }*/



       /* if(sSearch.equals("")){
            lst =  personService.getAll();
        }else {
            lst = personService.getPersonByDep(department);
        }*/
        if(department!=null&&!department.equals("1")){
            lst = personService.getPersonByDep(department);
        }else{
            lst = personService.getAll();
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
