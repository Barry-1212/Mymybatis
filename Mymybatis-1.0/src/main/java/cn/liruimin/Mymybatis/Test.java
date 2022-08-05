package cn.liruimin.Mymybatis;

import cn.liruimin.Mymybatis.entity.User;
import cn.liruimin.Mymybatis.mapper.UserMapper;
import cn.liruimin.Mymybatis.mybatis.session.MySqlSession;
import cn.liruimin.Mymybatis.mybatis.session.MySqlSessionFactory;
import cn.liruimin.Mymybatis.mybatis.session.MySqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        //第一步：读取mybatis-config.xml配置文件
        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-application.xml");

        //第二步：构建SqlSessionFactory
        MySqlSessionFactory sqlSessionFactory = new MySqlSessionFactoryBuilder().build(inputStream);

        //第三步：打开MySqlSession
        MySqlSession mySqlSession = sqlSessionFactory.openSession();

        //第四步：获取Mapper接口对象
        UserMapper userMapper = mySqlSession.getMapper(UserMapper.class);



        //第五步：调用Mapper接口对象的方法操作数据库
        User user = userMapper.getUserById(1);

        System.out.println(user.toString());

    }

}
