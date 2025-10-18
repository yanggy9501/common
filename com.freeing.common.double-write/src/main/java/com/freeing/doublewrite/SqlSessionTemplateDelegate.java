package com.freeing.doublewrite;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.mybatis.spring.SqlSessionUtils.closeSqlSession;
import static org.mybatis.spring.SqlSessionUtils.getSqlSession;
import static org.mybatis.spring.SqlSessionUtils.isSqlSessionTransactional;

/**
 * 双写工具：
 */
public class SqlSessionTemplateDelegate extends SqlSessionTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionTemplateDelegate.class);

    private static final String FILED_NAME = "originProxy";

    /**
     * 自定义的 SqlSessionProxy，在其切面中实现双写双读的迁移逻辑
     */
    private SqlSession customerSqlSessionProxy;

    /**
     * SqlSessionTemplate 原生 sqlSessionProxy
     */
    private SqlSession originSqlSessionProxy;

    private DoubleReadWriter doubleRw;

    public SqlSessionTemplateDelegate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
        this.customerSqlSessionProxy = (SqlSession) newProxyInstance(
            SqlSessionFactory.class.getClassLoader(),
            new Class[]{SqlSession.class},
            new CustomerSqlSessionInterceptor());
    }


    public void init() {
        Class<?> clazz = this.getClass().getSuperclass();
        try {
            Field field = clazz.getDeclaredField(FILED_NAME);
            field.setAccessible(true);
            this.originSqlSessionProxy = (SqlSession) field.get(this);

        } catch (Exception e) {

        }
    }

    /**
     * SqlSession proxy target InvocationHandler
     */
    private class CustomerSqlSessionInterceptor implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String className = method.getDeclaringClass().getName();
            if (className.equals("java.lang.Object")) {
                // TODO
            }
            // 异步执行“从”库读写
            Future<CombineResult> future = doubleRw.getThreadPool().submit(() -> {
                Object rst = null;
                long secondaryCost = 0L;
                try {
                    long secondaryStart = System.currentTimeMillis();
                    rst = invokeSecondaryDb(proxy, method, args);
                    secondaryCost = System.currentTimeMillis() - secondaryStart;
                } catch (Throwable th) {
                    // TODO
                    String comparePointName = "";
                    LOGGER.error("invoke secondary db error! mapper={}, args={}", comparePointName, "", th);
                } finally {

                }
                return new CombineResult(rst, secondaryCost);
            });

            long start = System.currentTimeMillis();
            Object result = invokeOrigin(proxy, method, args);
            long cost = System.currentTimeMillis() - start;
            // TODO 比对
            try {

            } catch (Throwable th) {

            }

            return result;
        }
    }

    /**
     * @see SqlSessionInterceptor#invoke(Object, Method, Object[])
     */
    private Object invokeOrigin(Object proxy, Method method, Object[] args) throws Throwable {
        SqlSession sqlSession = getSqlSession(getSqlSessionFactory(),
            getExecutorType(),
            getPersistenceExceptionTranslator());

        try {
            Object result = method.invoke(sqlSession, args);
            if (!isSqlSessionTransactional(sqlSession, getSqlSessionFactory())) {
                // force commit even on non-dirty sessions because some databases require
                // a commit/rollback before calling close()
                sqlSession.commit(true);
            }
            return result;
        } catch (Throwable t) {
            Throwable unwrapped = unwrapThrowable(t);
            if (getPersistenceExceptionTranslator() != null && unwrapped instanceof PersistenceException) {
                // release the connection to avoid a deadlock if the translator is no loaded. See issue #22
                closeSqlSession(sqlSession, getSqlSessionFactory());
                sqlSession = null;
                Throwable translated = getPersistenceExceptionTranslator()
                    .translateExceptionIfPossible((PersistenceException) unwrapped);
                if (translated != null) {
                    unwrapped = translated;
                }
            }
            throw unwrapped;
        } finally {
            if (sqlSession != null) {
                closeSqlSession(sqlSession, getSqlSessionFactory());
            }
        }
    }

    private Object invokeSecondaryDb(Object proxy, Method method, Object[] args) throws Throwable {
        if (isSelectWithResultHandler(method)) {
            throw new RuntimeException("DoubleReadWriter: 禁止对用定义 ResultHandler 值查询方法进行双读，这会导致 ResultHandler 逻辑执行两遍");
        }
        if (isSelectCursor(method)) {
            throw new RuntimeException("DoubleReadWriter: 禁止对用定义 selectCursor 值查询方法进行双读，双读的 Cursor 不会关闭，也无法对 Cursor中的内容进行比对!");
        }

        boolean isPrimary = false;
        SqlSessionFactory secondarySqlSessionFactory = doubleRw.selectSecondarySqlSessionFactory(isPrimary);
        SqlSession sqlSession = getSqlSession(secondarySqlSessionFactory, getExecutorType(), getPersistenceExceptionTranslator());
        try {
            Object result = method.invoke(sqlSession, args);
            if (!isSqlSessionTransactional(sqlSession, secondarySqlSessionFactory)) {
                // force commit even on non-dirty sessions because some databases require
                // a commit/rollback before calling close()
                sqlSession.commit(true);
            }
            return result;
        } catch (Throwable t) {
            Throwable unwrapped = unwrapThrowable(t);
            if (getPersistenceExceptionTranslator() != null && unwrapped instanceof PersistenceException) {
                // release the connection to avoid a deadlock if the translator is no loaded. See issue #22
                closeSqlSession(sqlSession, getSqlSessionFactory());
                sqlSession = null;
                Throwable translated = getPersistenceExceptionTranslator()
                    .translateExceptionIfPossible((PersistenceException) unwrapped);
                if (translated != null) {
                    unwrapped = translated;
                }
            }
            throw unwrapped;
        } finally {
            if (sqlSession != null) {
                closeSqlSession(sqlSession, getSqlSessionFactory());
            }
        }
    }

    private boolean isSelectWithResultHandler(Method method) {
        return "select".equals(method.getName());
    }

    private boolean isSelectCursor(Method method) {
        return "selectCursor".equals(method.getName());
    }
}
