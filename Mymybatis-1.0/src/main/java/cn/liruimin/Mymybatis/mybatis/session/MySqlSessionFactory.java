package cn.liruimin.Mymybatis.mybatis.session;


import cn.liruimin.Mymybatis.mybatis.executor.MyExecutor;
import cn.liruimin.Mymybatis.mybatis.mapping.MyConfiguration;
import lombok.AllArgsConstructor;

import java.sql.SQLException;

@AllArgsConstructor
public class MySqlSessionFactory {

    private MyConfiguration myConfiguration;

    /**
     *
     * 打开SqlSession
     * @return
     */
    public MySqlSession openSession() throws SQLException {
        //创建SqlSession需要做些什么呢
        MyExecutor myExecutor = new MyExecutor(this.myConfiguration);
        return new MySqlSession(this.myConfiguration, myExecutor);
    }

}
