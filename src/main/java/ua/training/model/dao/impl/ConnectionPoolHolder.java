package ua.training.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

class ConnectionPoolHolder {
    private static final String JDBC_MYSQL_ADDRESS = "jdbc:mysql://localhost:3306/food_tracking";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static volatile DataSource dataSource;

    static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(JDBC_MYSQL_ADDRESS);
                    ds.setUsername(JDBC_USERNAME);
                    ds.setPassword(JDBC_PASSWORD);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }


}
