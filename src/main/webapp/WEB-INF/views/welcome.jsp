<%--
  Created by IntelliJ IDEA.
  User: zhangd
  Date: 2017/5/24
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量
    String basePath = request.getScheme()+"://"+request.getServerName()
            +":"+request.getServerPort()+path+"/";
// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。
    pageContext.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>welcome to 华信数据</title>
    <script type="text/javascript" charset="utf8" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.js"></script>
<%--
    <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
--%>
   <%-- <link href="http://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">--%>
    <%--
            <script type="text/javascript" charset="utf8" src="<%=basePath%>DataTables-1.10.15/media/js/jquery.dataTables.min.js"></script>
            <script type="text/javascript" charset="utf8" src="<%=basePath%>DataTables-1.10.15/media/js/jquery.js"></script>--%>
            <script type="text/javascript" charset="utf8" src="<%=basePath%>DataTables-1.10.15/media/js/bootstrap.min.js"></script>
            <script type="text/javascript" charset="utf8" src="<%=basePath%>DataTables-1.10.15/media/js/dataTables.bootstrap.js"></script>
            <link href="<%=basePath%>DataTables-1.10.15/media/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
            <link href="<%=basePath%>DataTables-1.10.15/media/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <link href="<%=basePath%>DataTables-1.10.15/media/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<body>
        <table id="table_id_example" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th><input type="checkbox" id="userCheckAll"></th>
                <th>id</th>
                <th>password</th>
                <th>username</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Row 1 Data 1</td>
                    <td>Row 1 Data 2</td>
                </tr>
                <tr>
                    <td>Row 2 Data 1</td>
                    <td>Row 2 Data 2</td>
                </tr>
            </tbody>
        </table>

    <!-- 按钮触发模态框 -->
    <button type="button" class="btn btn-info" id="initData" data-toggle="modal" data-target="#myModal" style="background-color:#255625">添加数据</button>
    <button type="button" class="btn btn-primary" id="edit" data-toggle="modal" data-target="#myModal" style="background-color: #255625">编辑选中行</button>
    <button type="button" id="delete" class="btn btn-danger" style="background-color: #255625">删除选中行</button>
    <button type="button" id="deleteAll" class="btn btn-danger" style="background-color: #255625">批量删除</button>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span--%>
                            <%--aria-hidden="true">&times;</span></button>--%>
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" class="form-control"  id="id" placeholder="id">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control"  id="password" placeholder="password">
                    </div>
                    <div class="form-group">
                        <%--<input type="text" class="form-control" id="username" placeholder="username">--%>
                            <td><select class="form-control" onchange="onchange1(this)" style="width:250px" id="username">
                                <option>张三</option>
                                <option>李四</option>
                                <option>王五</option>
                                <option>陈六</option>
                            </select></td>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="shutDown">关闭</button>
                    <button type="button" class="btn btn-primary" id="save">保存</button>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    var table;
    var editFlag = false;
    $(document).ready( function () {
        table=$('#table_id_example').DataTable({
            "bAutoWidth": true,                    //自适应宽度
            "bProcessing": false,                   // 是否显示取数据时的那个等待提示
            "bServerSide": true,                    //这个用来指明是通过服务端来取数据
            "searchable": true,//开启搜索
            "bSortable":false,
            "asSorting":false,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "未搜索到数据",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "columns" : [
                {
                "data": "id",
                "createdCell": function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html("<input type='checkbox' name='checkList' value='" + sData + "'>");
                }
            },
                {
                "data" : "id"
            }, {
                "data" : "password"
            }, {
                "data" : "username"
            }],
            "columnDefs": [
                {"targets": [ 0 ],
                    "data": "id",
                    "orderable": false,
                   /* "render": function ( data, type, full, meta ) {
                       return '<input type="checkbox" value="' + data + '" title="' + data + '" id="checkbox" />';
                    }*/
                }
            ],
            "sAjaxSource": "../tableDemoAjax",      //这个是请求的地址
            "fnServerData": retrieveData            // 获取数据的处理函数
        });

        $('#table_id_example tfoot th').each( function () {
            var title = $('#table_id_example thead th').eq( $(this).index() ).text();
            alert("title="+title);
            $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
        } );
      //  checkbox("userCheckAll");

       $("#save").click(add);
       $("#shutDown").click(shutDown);
    } );
    function retrieveData( sSource,aoData, fnCallback) {
        $.ajax({
            url : sSource,                              //这个就是请求地址对应sAjaxSource
            data : {"aoData":JSON.stringify(aoData)},   //这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数 ,分页,排序,查询等的值
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(result) {
                fnCallback(result);
                //把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error : function(msg) {
            }
        });
    }

    //checkbox全选
    $("#userCheckAll").click(function () {
        if ($(this).prop("checked")) {
            $("input[name='checkList']").prop("checked", true);
            $("tr").addClass('selected');
        } else {
            $("input[name='checkList']").prop("checked", false);
            $("tr").removeClass('selected');
        }
    });
    /**
     * 添加数据
     **/
    function add() {
        var addJson = {
            "id":$("#id").val(),
            "username": $("#username").val(),
            "password": $("#password").val(),
        };
        ajax(addJson);
    }

    /**
     * 关闭
     * */
    function shutDown(){
        clear();
    }
    /*
    *选中一行
    * */
    $('#table_id_example tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            $(this).find('input').prop("checked",false);
        }
        else {
           // table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            $(this).find('input').prop("checked",true);
        }
    } );

    /*
    * 删除
    * */
    $('#delete').click( function () {
        var id = table.$('tr.selected').find("td:eq(1)").text();
        if(id == null || id == '') {
            alert("请选择删除的行！");
            return false;
        }else{
            $.ajax({
                type: 'post',
                url: '../user/delete?id='+id,
             //   dataType:'json',
                success:function(data){
                    table.ajax.reload();
                }
            });
        }
    } );
    //批量删除
    $('#deleteAll').click(function(){
        var idList = "";
        $.each($('input:checkbox[name=checkList]:checked'),function(i){
            if(i==0){
                idList += $(this).val();
             }else{
                idList += ","+$(this).val();
            }
        });
        if(idList!=null && idList != ""){
            $.ajax({
                type: 'post',
                url: '../user/deleteAll',
                data: {id:idList},
                success:function(data){
                    table.ajax.reload();
                }
            });
        }else{
            alert("请选中要删除的行");
        }
    });
    /*
    * 编辑
    * */
    $('#edit').click(function(){
        editFlag = true;
        var id = table.$('tr.selected').find("td:eq(1)").text();
        var password = table.$('tr.selected').find("td:eq(2)").text();
        var username = table.$('tr.selected').find("td:eq(3)").text();
        if(id == null || id == ''){
            alert("请选择要编辑的行");
            return false;
        }else{
            $('#id').val(id);
            $('#password').val(password);
            $('#username').val(username);
            $('#myModalLabel').html("编辑");
        }
    });
    function ajax(obj) {
        var url ="../user/save" ;
        if(editFlag){
            url = "../user/update";
        }
        $.ajax({
            url:url ,
            data: {
                "id":obj.id,
                "username": obj.username,
                "password": obj.password,
            }, success: function (data) {
                table.ajax.reload();
                $("#myModal").modal("hide");
                $("#myModalLabel").text("新增");
                clear();
                console.log("结果" + data);
            }
        });
    }

    //清除
    function clear() {
        $("#id").val("").attr("disabled",false);
        $("#username").val("");
        $("#password").val("");
        editFlag = false;
    }


    /*
    * 下拉框改变事件
    * */
    function onchange(obj){
        alert("改变事件");
        $.ajax({
            type :"post",
            url : "../user/getSelect",
        });
    }
    //选中一行 checkbox选中
    function checkbox(tableId) {
        //每次加载时都先清理
        $('#' + tableId + ' tbody').off("click", "tr");
        $('#' + tableId + ' tbody').on("click", "tr", function () {
            $(this).toggleClass('selected');
            if ($(this).hasClass("selected")) {
                $(this).find("input").prop("checked", true);
            } else {
                $(this).find("input").prop("checked", false);
            }
        });
    }
    /*$('#table_id_example').on('init.dt',function(){
        alert("加载完成时间为"+getNowFormatDate());
    });*/

    /**
    * 获取当前时间
     * * @returns {string}
     */
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
        return currentdate;
    }
</script>
</html>
