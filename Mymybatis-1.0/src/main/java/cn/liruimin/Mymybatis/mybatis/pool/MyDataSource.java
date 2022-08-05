package cn.liruimin.Mymybatis.mybatis.pool;

import cn.liruimin.Mymybatis.mybatis.mapping.MyEnvironment;
import lombok.Data;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

@Data
public class MyDataSource implements MyDataSourceInterface{

    private MyEnvironment myEnvironment;

    private List<Connection> pool;

    private Connection conn = null;

    private static MyDataSource instance;

    private static final int POOL_SIZE = 15;

    private MyDataSource(MyEnvironment myEnvironment) {
        this.myEnvironment = myEnvironment;
        pool = new Vector<>(15);
        initPool();
    }

    /**
     * 单例模式
     * @param myEnvironment
     * @return
     */
    public static MyDataSource getInstance(MyEnvironment myEnvironment) {
        if(instance == null) {
            instance = new MyDataSource(myEnvironment);
        }
        return instance;
    }

    /**
     * 获取连接池的连接
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Connection getConnection() throws SQLException {
        if(pool.size() > 0){
            Connection connection = pool.get(0);
            pool.remove(connection);
            return connection;
        }else{
            return null;
        }
    }


    /**
     * 初始化连接池
     */
    private void initPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = this.createConnection();
            pool.add(connection);
        }
    }



    /**
     * 创建连接
     * @return
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            Driver driver = (Driver) Class.forName(myEnvironment.getDriver()).newInstance();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(myEnvironment.getUrl(), myEnvironment.getUsername(), myEnvironment.getPassword());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //归还连接池
    public void release(Connection connection) {
        pool.add(connection);
    }
}
