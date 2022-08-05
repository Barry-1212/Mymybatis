package cn.liruimin.Mymybatis.mybatis.executor;

import cn.liruimin.Mymybatis.entity.User;
import cn.liruimin.Mymybatis.mybatis.mapping.MapperStatement;
import cn.liruimin.Mymybatis.mybatis.mapping.MyConfiguration;
import cn.liruimin.Mymybatis.mybatis.pool.MyDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class MyExecutor {

    /**
     * 数据源
     */
    private MyDataSource myDataSource;

    public MyExecutor(MyConfiguration myConfiguration) throws SQLException {
        //初始化数据源
        this.myDataSource = MyDataSource.getInstance(myConfiguration.getMyEnvironment());
    }

    /**
     * 查询数据库
     * @param mapperStatement
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> query(MapperStatement mapperStatement, Object args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> resultList = new ArrayList<>();
        try {
            connection = myDataSource.getConnection();

            String sql = mapperStatement.getSql();
            while(sql.indexOf("#") != -1) {
                int i = sql.indexOf("#");
                int j = sql.indexOf("}");
                sql = sql.replace('}','?');
                String temp = sql.substring(i, j);
                sql = sql.replace(temp, "");
            }
            preparedStatement = connection.prepareStatement(sql);

            if(args instanceof Integer) {
                preparedStatement.setInt(1, (Integer) args);
            }else if(args instanceof Long) {
                preparedStatement.setLong(1, (Long) args);
            }else if(args instanceof Float) {
                preparedStatement.setFloat(1, (Float) args);
            }
            else if(args instanceof Double) {
                preparedStatement.setDouble(1, (Double) args);
            }
            else if(args instanceof String) {
                preparedStatement.setString(1, (String) args);
            }
            //执行查询
            resultSet = preparedStatement.executeQuery();

            handleResultSet(resultSet, resultList, mapperStatement.getResultType());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭PreparedStatement
        if(null != preparedStatement) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //归还连接到连接池
        if(null != connection) {
            myDataSource.release(connection);
        }

        return resultList;

    }

    /**
     * 处理查询的resultSet
     * @param resultSet
     * @param resultList
     * @param resultType
     * @param <T>
     */
    private <T> void handleResultSet(ResultSet resultSet, List<T> resultList, String resultType) {
        try {
            //User类
            Class<T> clazz = (Class<T>) Class.forName(resultType);

            while(resultSet.next()) {
                T entity = clazz.newInstance();
                //id    name    age     gender
                //getId() getName() getAge() getGender()
                ResultSetMetaData metaData = resultSet.getMetaData();
                int count = metaData.getColumnCount();
                List<Method> setMethods = new ArrayList<>(count);
                for (Method method : clazz.getMethods()) {
                    if(method.getName().indexOf("set") == 0) {
                        //拿到set方法
                        setMethods.add(method);
                    }
                }
                for (int i = 1; i <= count; i++) {
                    for (Method setMethod : setMethods) {
                        String column = setMethod.getName().split("set")[1];
                        Object object = resultSet.getObject(column);
                        setMethod.invoke(entity, object);
                    }
                }
                resultList.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
