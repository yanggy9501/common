package com.freeing.common.component.util;

import com.freeing.common.component.constant.StrPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 资源工具类
 *
 * @author yanggy
 */
public class ResourceUtils {

    /**
     * 获取项目绝对路径，即jar包所在路径。如果工程是部署在tomcat服务上则返回的是tomcat安装目录下的 bin路径.
     *
     * @return String
     */
    public static String rootPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取资源
     *
     * @param resource 资源
     * @param classLoader 类加载器
     * @return
     */
    public static InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        ClassLoader[] classLoaders =  getClassLoader(classLoader);
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                InputStream returnValue = loader.getResourceAsStream(resource);
                if (returnValue == null) {
                    returnValue = loader.getResourceAsStream("/" + resource);
                }
                if (returnValue != null){
                    return returnValue;
                }
            }
        }
        return null;
    }

    /**
     * 读取资源为字符串
     *
     * @param classLoader 类加载器
     * @param resource 资源
     * @return 输入流
     */
    public static String readAsString(ClassLoader classLoader, String resource) {
        InputStream inputStream = getResourceAsStream(resource, classLoader);
        if (inputStream == null) {
            return StrPool.EMPTY;
        }
        String res;
        try {
            res = IOUtils.toString(inputStream);
        } catch (IOException e) {
            res = StrPool.EMPTY;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return res;
    }

    /**
     * 从 classpath 读取 properties 文件
     *
     * @param classLoader 类加载器
     * @param resource 资源
     * @return 输入流
     */
    public static Properties readAsProperties(ClassLoader classLoader, String resource) {
        Properties properties = new Properties();
        InputStream inputStream = getResourceAsStream(resource, classLoader);
        try {
            properties.load(inputStream);
        } catch (IOException ignored) {

        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return properties;
    }

    private static ClassLoader[] getClassLoader(ClassLoader classLoader) {
        return new ClassLoader[] {
            classLoader,
            Thread.currentThread().getContextClassLoader(),
            ClassLoader.getSystemClassLoader()
        };
    }
}
