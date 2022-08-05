package cn.liruimin.Mymybatis.mybatis.proxy;

import cn.liruimin.Mymybatis.mybatis.session.MySqlSession;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class MapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果是Object类直接放行
        if(Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        Class<?> clazz = method.getReturnType();

        //判断
        if(Collection.class.isAssignableFrom(clazz)) {
            //返回类型是集合
            return null;
        }else if(Map.class.isAssignableFrom(clazz)){
            //返回类型是Map
            return null;
        }else {
            //返回对象数据
            //找到sql语句map的key
            String statementKey =  method.getDeclaringClass().getName() + "." + method.getName();
            return mySqlSession.selectOne(statementKey, args == null ? null : args[0]);
        }
    }


}
