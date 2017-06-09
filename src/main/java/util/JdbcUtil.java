package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhangd on 2017/6/5.
 */
public class JdbcUtil {
    public void dbConnection(){
        String url = "jdbc:mysql://localhost:3306/test" ;
        String username = "root" ;
        String password = "" ;
        try{
            Connection con =
                    DriverManager.getConnection(url, username, password) ;
        }catch(SQLException se){
            System.out.println("数据库连接失败！");
            se.printStackTrace() ;
        }
    }
    public static  void main(String args[]){
        JdbcUtil a = new JdbcUtil();
        a.dbConnection();
    }

}
