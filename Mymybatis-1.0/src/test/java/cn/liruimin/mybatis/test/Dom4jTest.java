package cn.liruimin.mybatis.test;


import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class Dom4jTest {

    @Test
    public void test01() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(Dom4jTest.class.getClassLoader().getResourceAsStream("mybatis-application.xml"));
        Element rootElement = document.getRootElement();
        List<Node> nodes = rootElement.selectNodes(".//property");
        for (Node node : nodes) {
            Element element = (Element)node;
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            System.out.println(name+"---"+value);
        }
        List<Node> mappers = rootElement.selectNodes(".//mapper");
        for (Node mapper : mappers) {
            Element element = (Element) mapper;
            String resource = element.attributeValue("resource");
            System.out.println("resource---" + resource);
        }

        System.out.println("----------------------------------------");
//        --------------------------------------------------------------------------------

        SAXReader mapperSAXReader = new SAXReader();
        Document mapperDocument = mapperSAXReader.read(Dom4jTest.class.getClassLoader().getResourceAsStream("cn/liruimin/Mymybatis/mapper/UserMapper.xml"));
        Element root = mapperDocument.getRootElement();
        String namespace = root.attributeValue("namespace");
        System.out.println(namespace);
        List<Node> nodeList = root.selectNodes("./select");
        for (Node node : nodeList) {
            Element element = (Element)node;
            String id = element.attributeValue("id");
            String sql = element.getText();
            System.out.println(id);
            System.out.println(sql);
        }


    }

}
