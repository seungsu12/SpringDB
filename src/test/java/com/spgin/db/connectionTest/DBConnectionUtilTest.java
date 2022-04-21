package com.spgin.db.connectionTest;

import com.spgin.db.connection.ConnectionConst;
import com.spgin.db.connection.DBConnectionUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.spgin.db.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connection() {
        Connection connection = DBConnectionUtil.getConnection();
        Assertions.assertThat(connection).isNotNull();
    }

    @Test
    void hikari() throws SQLException, InterruptedException {
        HikariDataSource dataSource =new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setPassword(PASSWORD);
        dataSource.setUsername(USERNAME);

        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");
        useDataSource(dataSource);
        Thread.sleep(1000);

    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection c1 = dataSource.getConnection();
        Connection c2 = dataSource.getConnection();
        log.info("connection ={} class={}",c1,c1.getClass());
        log.info("connection ={} class={}",c2,c2.getClass());
    }
}
