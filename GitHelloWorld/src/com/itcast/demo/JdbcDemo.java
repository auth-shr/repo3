package com.itcast.demo;

import com.itcast.domain.User;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDemo {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        List<User> list = null;

        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接对象
            conn = DriverManager.getConnection("jdbc:mysql:///db1", "root", "123456");
            //获得预处理对象
            String sql = "select * from user where id = ?";
            stat = conn.prepareStatement(sql);
            stat.setInt(1,1);
            //执行sql语句
            res = stat.executeQuery();
            //处理结果集
            list = new ArrayList<>();
            while(res.next()){
                User user = new User();
                list.add(user);
                Integer id = res.getInt(1);
                String username = res.getString(2);
                String password = res.getString(3);
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            if(res!=null){
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for (User user : list) {
            System.out.println(user);
        }

    }
}
