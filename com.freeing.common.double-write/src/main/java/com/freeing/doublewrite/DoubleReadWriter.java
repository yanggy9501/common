package com.freeing.doublewrite;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.concurrent.ThreadPoolExecutor;

public class DoubleReadWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleReadWriter.class);

    private ThreadPoolExecutor threadPool;

    private SqlSessionFactory primarySqlSessionFactory;

    private SqlSessionFactory secondarySqlSessionFactory;

    public DoubleReadWriter(ThreadPoolExecutor threadPool,
        SqlSessionFactory primarySqlSessionFactory,
        SqlSessionFactory secondarySqlSessionFactory) {

        DataSource primaryDataSource = primarySqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
        DataSource secondaryDataSource = secondarySqlSessionFactory.getConfiguration().getEnvironment().getDataSource();

//        if (primaryDataSource instanceof DynamicDataSource || backupDataSource instanceof DynamicDataSource) {
//
//        }

        this.threadPool = threadPool;
        this.primarySqlSessionFactory = primarySqlSessionFactory;
        this.secondarySqlSessionFactory = secondarySqlSessionFactory;
    }

    public SqlSessionFactory selectSecondarySqlSessionFactory(boolean isPrimary) {
        return isPrimary ? primarySqlSessionFactory : secondarySqlSessionFactory;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public SqlSessionFactory getPrimarySqlSessionFactory() {
        return primarySqlSessionFactory;
    }

    public void setPrimarySqlSessionFactory(SqlSessionFactory primarySqlSessionFactory) {
        this.primarySqlSessionFactory = primarySqlSessionFactory;
    }

    public SqlSessionFactory getSecondarySqlSessionFactory() {
        return secondarySqlSessionFactory;
    }

    public void setSecondarySqlSessionFactory(SqlSessionFactory secondarySqlSessionFactory) {
        this.secondarySqlSessionFactory = secondarySqlSessionFactory;
    }
}
