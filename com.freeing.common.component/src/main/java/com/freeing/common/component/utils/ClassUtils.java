package com.freeing.common.component.utils;

import com.freeing.common.component.constants.StrPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * class 工具类
 *
 * @author yanggy
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
    /**
     * 获取项目绝对路径，即jar包所在路径。如果工程是部署在tomcat服务上则返回的是tomcat安装目录下的 bin路径.
     *
     * @return String
     */
    public static String rootPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 从 classpath 读取文件
     *
     * @param classLoader 类加载器
     * @param fileName 文件全名
     * @return 输入流
     */
    public static String readAsStringFromClassPath(ClassLoader classLoader, String fileName) {
        InputStream resource = classLoader.getResourceAsStream("/" + fileName);
        if (resource == null) {
            return StrPool.EMPTY;
        }
        String res;
        try {
            res = IOUtils.toString(resource);
        } catch (IOException e) {
            res = StrPool.EMPTY;
        } finally {
            IOUtils.closeQuietly(resource);
        }
        return res;
    }

    /**
     * 从 classpath 读取 properties 文件
     *
     * @param classLoader 类加载器
     * @param fileName 文件全名
     * @return 输入流
     */
    public static Properties readAsPropertiesFromClassPath(ClassLoader classLoader, String fileName) {
        Properties properties = new Properties();
        InputStream resource = classLoader.getResourceAsStream(File.separator + fileName);
        try {
            properties.load(resource);
        } catch (IOException ignored) {

        } finally {
            IOUtils.closeQuietly(resource);
        }
        return properties;
    }
}
