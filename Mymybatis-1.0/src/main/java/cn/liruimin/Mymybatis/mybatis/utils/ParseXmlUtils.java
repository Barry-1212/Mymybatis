package cn.liruimin.Mymybatis.mybatis.utils;

import cn.liruimin.Mymybatis.mybatis.mapping.MapperStatement;
import cn.liruimin.Mymybatis.mybatis.mapping.MyConfiguration;
import cn.liruimin.Mymybatis.mybatis.mapping.MyEnvironment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseXmlUtils {

    public static MyConfiguration parseXml(InputStream inputStream) throws DocumentException {
        //解析两个xml文件，构建MyConfiguration对象

        //1、解析mybatis-application.xml文件,构建MyEnvironment对象
        MyEnvironment myEnvironment = new MyEnvironment();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Node> nodes = rootElement.selectNodes(".//property");
        //driver
        Element driver = (Element) nodes.get(0);
        myEnvironment.setDriver(driver.attributeValue("value"));
        //url
        Element url = (Element) nodes.get(1);
        myEnvironment.setUrl(url.attributeValue("value"));
        //username
        Element username = (Element) nodes.get(2);
        myEnvironment.setUsername(username.attributeValue("value"));
        //password
        Element password = (Element) nodes.get(3);
        myEnvironment.setPassword(password.attributeValue("value"));


        //2、解析UserMapper.xml文件,构建MapperStatement对象
        MapperStatement mapperStatement = new MapperStatement();


        InputStream mapperStream = ParseXmlUtils.class.getClassLoader().getResourceAsStream("cn/liruimin/Mymybatis/mapper/UserMapper.xml");
        Document read = saxReader.read(mapperStream);
        Element root = read.getRootElement();
        String namespace = root.attributeValue("namespace");
        System.out.println(namespace);
        List<Node> nodeList = root.selectNodes("./select");
        for (Node node : nodeList) {
            Element element = (Element)node;
            String id = element.attributeValue("id");
            mapperStatement.setId(id);
            String resultType = element.attributeValue("resultType");
            mapperStatement.setResultType(resultType);
            String sql = element.getText();
            mapperStatement.setSql(sql);
        }
        mapperStatement.setNamespace(namespace);


        MyConfiguration myConfiguration = new MyConfiguration();
        myConfiguration.setMyEnvironment(myEnvironment);

        Map<String,MapperStatement> map = new HashMap<String, MapperStatement>();
        map.put(mapperStatement.getNamespace()+"."+mapperStatement.getId(), mapperStatement);

        myConfiguration.setMapperStatementMap(map);

        return myConfiguration;
    }

}
