package com.freeing.common.component.utils;

import com.freeing.common.component.constants.StrPool;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * class 工具类
 *
 * @author yanggy
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {

    private static final String SET_METHOD_PREFIX = "set";

    private static final Collection<Class<?>> GENERAL_CLASS_TYPE;

    static {
        GENERAL_CLASS_TYPE = Sets.newHashSet(boolean.class, Boolean.class,
            int.class, Integer.class,
            long.class, Long.class,
            double.class, Double.class,
            BigDecimal.class,
            Date.class,
            String.class);
    }

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
    public static String readAsString(ClassLoader classLoader, String fileName) {
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
    public static Properties readAsProperties(ClassLoader classLoader, String fileName) {
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

    /**
     * 拼接属性的 setter 方法
     * 案例：
     * fieldName --> setFieldName
     * field_name --> setFieldName
     *
     * @param fieldName 属性名
     * @return setter method name
     */
    public static String getSetterMethodName(final String fieldName) {
        if (fieldName.contains("_")) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, SET_METHOD_PREFIX + "_" + fieldName);
        }
        return SET_METHOD_PREFIX + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 调用 object setter 方法
     *
     * @param object object
     * @param methodName method of object
     * @param setterValue setter value，必须是基础数据类型
     */
    public static void callSetterMethod(final Object object, final String methodName, final String setterValue) {
        for (Class<?> each : GENERAL_CLASS_TYPE) {
            try {
                Method method = object.getClass().getMethod(methodName, each);
                if (boolean.class == each || Boolean.class == each) {
                    method.invoke(object, Boolean.valueOf(setterValue));
                } else if (int.class == each || Integer.class == each) {
                    method.invoke(object, Integer.parseInt(setterValue));
                } else if (long.class == each || Long.class == each) {
                    method.invoke(object, Long.parseLong(setterValue));
                } else if (double.class == each || Double.class == each) {
                    method.invoke(object, Double.parseDouble(setterValue));
                } else if (BigDecimal.class == each) {
                    method.invoke(object, new BigDecimal(setterValue));
                } else if (Date.class == each && StringUtils.isNotBlank(setterValue)) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        method.invoke(object, simpleDateFormat.parse(setterValue));
                    } catch (ParseException ignored) {

                    }
                } else {
                    method.invoke(object, setterValue);
                }
                return;
            } catch (final ReflectiveOperationException ignored) {

            }
        }
    }

    /**
     * 获取 class类对象以及父类对象的所有成员属性Field
     *
     * @param clazz 字节码
     * @return Field[]
     */
    public static Field[] getAllFields(Class<?> clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
