package cn.liruimin.Mymybatis.mybatis.session;

import cn.liruimin.Mymybatis.mybatis.mapping.MyConfiguration;
import cn.liruimin.Mymybatis.mybatis.utils.ParseXmlUtils;
import org.dom4j.DocumentException;

import java.io.InputStream;

public class MySqlSessionFactoryBuilder {

    public MySqlSessionFactory build(InputStream inputStream) throws DocumentException {
        //如何构造MySqlSessionFactory对象

        //解析两个xml文件,构建MyConfiguration对象
        MyConfiguration myConfiguration = ParseXmlUtils.parseXml(inputStream);
        return new MySqlSessionFactory(myConfiguration);
    }
}
