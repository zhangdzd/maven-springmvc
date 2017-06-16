package com.example.controller;

import com.example.pojo.Department;
import com.example.pojo.Person;
import com.example.service.DepartmentServiceImpl;
import com.example.service.PersonServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangd on 2017/6/13.
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @RequestMapping("getAll")
    public String getAllUser(HttpServletRequest req,HttpServletResponse res){
        // res.setContentType("application/json;charset=UTF-8");
        List<Person> list=personService.getAll();
        for (Person person:list){
          //   System.out.println(person);
          //   System.out.println(person.getId()+person.getName()+person.getDepartment().getName());
        }
        return "jsTreeDemo";
    }
    @RequestMapping("getTree")
    @ResponseBody
    public JSONArray getTree(HttpServletRequest request,String parentId){
        //前台传入的id
        String startId = request.getParameter("id")==null?"":request.getParameter("id");
        System.out.println(startId);
        List<Department> list = new ArrayList();
        //创建一个json数组对象
        JSONArray jsonArray = new JSONArray();
        /*Integer id = null;
        if(parentId != null && !"".equals(parentId)){
            id =  Integer.parseInt(parentId);
        }else{

        }*/
        if(startId.equals("#")&&parentId==null){
            //开始查父节点的第一条数据
            list =departmentService.getDepByParentId("0");
        }else{
            //根据list和parentid查出数据
            list =departmentService.getDepByParentId(parentId);
        }
        //遍历department得到一个list
        for(Department department:list){
            //创建一个json对象
            JSONObject currOrg_jo = new JSONObject();
            currOrg_jo.put("id",department.getId());
            currOrg_jo.put("text",department.getName());
            JSONArray json = getTree(request,String.valueOf(department.getId()));
            if(json.size()>0){
                JSONObject state = new JSONObject();
                state.put("opened", true);
                currOrg_jo.put("state", state);
                currOrg_jo.put("children", json);
            }

            jsonArray.add(currOrg_jo);
        }

        return jsonArray;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(HttpServletRequest req,HttpServletResponse res){
        Long id = Long.parseLong(req.getParameter("id"));
        personService.delete(id);
        return "success";
    }
    @RequestMapping("deleteAll")
    @ResponseBody
    public String deleteAll(HttpServletRequest req,HttpServletResponse res){
        String idList = req.getParameter("id").replace("on","");
        String[] arr= idList.split(",");
        for(String a:arr){
            if(a!=null&&!"".equals(a)){
                long id = Long.parseLong(a);
                personService.delete(id);
            }
        }
        //  Integer id =Integer.parseInt(idList.)
        return "success";
    }
}
    /*public JSONArray findTypeNode(HttpServletRequest request,String parentId){
        JSONArray jsonArray = new JSONArray();
        Set<String> orgCodes = userRoleService.findOrgCodesByUserId(((User) request.getSession().getAttribute("currUser")).getUserId());
        List<Orgs> orgsAccessList = orgsService.findByOrgCodes(orgCodes);
        List<String> orgCodeList    = new ArrayList<String>();
        for(Orgs o : orgsAccessList){
            orgCodeList.add(o.getOrgCode());
        }

        Integer id = null;


        if(parentId != null && !"".equals(parentId)){
            id =  Integer.parseInt(parentId);
        }

        List<MaterialType> materialTypeList = materialTypeService.findAllByParentId(id,orgCodeList);
        if(id==null&&materialTypeList.size()==0){
            JSONObject currOrg_jo = new JSONObject();
            JSONObject state = new JSONObject();
            state.put("disabled", true);
            currOrg_jo.put("state", state);
            currOrg_jo.put("text","暂无物料类型!");
            currOrg_jo.put("id","-1");
            jsonArray.add(currOrg_jo);
            return jsonArray;
        }

        for(MaterialType materialType : materialTypeList){
            JSONObject currOrg_jo = new JSONObject();
            currOrg_jo.put("id", materialType.getId());
            List<Integer> typeIds = getAllTypes(materialType.getId());
            Long inStockNum = instoragedetailService.countInstorages(typeIds);
            Long outStockNum = outstoragedetailService.countOutstorages(typeIds);
            if(inStockNum == null){
                inStockNum = new Long(0);
            }
            if(outStockNum == null){
                outStockNum = new Long(0);
            }
            currOrg_jo.put("text", materialType.getName()+"("+ (inStockNum-outStockNum) +")");
            JSONArray json = findTypeNode(request,String.valueOf(materialType.getId()));
            if(json.size() > 0){
                JSONObject state = new JSONObject();
                state.put("opened", true);
                currOrg_jo.put("state", state);
                currOrg_jo.put("children", json);
            }
            jsonArray.add(currOrg_jo);
        }
        return jsonArray;
    }*/