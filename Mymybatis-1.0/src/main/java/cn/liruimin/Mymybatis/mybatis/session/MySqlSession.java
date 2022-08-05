package cn.liruimin.Mymybatis.mybatis.session;

import cn.liruimin.Mymybatis.mapper.UserMapper;
import cn.liruimin.Mymybatis.mybatis.executor.MyExecutor;
import cn.liruimin.Mymybatis.mybatis.mapping.MapperStatement;
import cn.liruimin.Mymybatis.mybatis.mapping.MyConfiguration;
import cn.liruimin.Mymybatis.mybatis.proxy.MapperProxy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Proxy;
import java.util.List;

@Data
@AllArgsConstructor
public class MySqlSession {

    /**
     * 构造方法初始化两个成员变量
     */

    private MyConfiguration myConfiguration;

    private MyExecutor myExecutor;


    /**
     * 获取Mapper接口的代理对象
     * @param mapperClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> mapperClass) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class<?>[] {mapperClass}, mapperProxy);
    }


    /**
     * 查询数据库
     * @param statementKey
     * @param args
     * @param <T>
     * @return
     */
    public <T> T selectOne(String statementKey, Object args) {
        MapperStatement mapperStatement = myConfiguration.getMapperStatementMap().get(statementKey);
        List<T> resultList = myExecutor.query(mapperStatement, args);
        if(resultList != null && resultList.size() > 1) {
            throw new RuntimeException("more than one");
        }else {
            return resultList.get(0);
        }
    }


}
