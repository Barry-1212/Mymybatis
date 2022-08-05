package cn.liruimin.Mymybatis.mybatis.mapping;

import lombok.Data;

import java.util.Map;

@Data
public class MyConfiguration {

    //mybatis-application.xml
    private MyEnvironment myEnvironment;



    //UserMapper.xml
    private Map<String, MapperStatement> mapperStatementMap;


}
