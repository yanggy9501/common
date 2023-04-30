package com.freeing.common.security;

import com.freeing.common.security.annotation.Encrypt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Function;

/**
 * 摘要工具
 *
 * @author yanggy
 */
public class EncryptUtils {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EncryptUtils.class);

    /**
     * MD5 摘要
     *
     * @param text 明文
     * @return
     */
    public static String encryptByMD5(String text) {
        return encrypt(text, "MD5");
    }

    /**
     * SHA-1 摘要
     *
     * @param text 明文
     * @return
     */
    public static String encryptBySHA1(String text) {
        return encrypt(text, "SHA-1");
    }

    /**
     * SHA-256 摘要
     *
     * @param text 明文
     * @return
     */
    public static String encryptBySHA256(String text) {
        return encrypt(text, "SHA-256");
    }

    /**
     * 摘要
     *
     * @param text 明文
     * @param encryptType 加密方式 MD5, SHA-1 SHA-256 默认 MD5
     * @return
     */
    private static String encrypt(String text, String encryptType) {
        MessageDigest md;
        String strDes;
        byte[] bts = text.getBytes();
        if (encryptType == null || encryptType.equals("")) {
            encryptType = "MD5";
        }
        try {
            md = MessageDigest.getInstance(encryptType);
            md.update(bts);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
        return strDes;
    }

    /**
     * base64 加密
     *
     * @param text
     * @return
     */
    public static String encryptByBase64(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode(text.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }

    /**
     * base64 解密
     *
     * @param text
     * @return
     */
    public static String decryptByBase64(String text) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(text.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }

    /**
     * 转 16 进制
     *
     * @param bytes
     * @return
     */
    private static String bytes2Hex(byte[] bytes) {
        StringBuilder des = new StringBuilder();
        String hex;
        for (byte aByte : bytes) {
            // 一个字节 8 bit，没4个bit作为一个16进制数，一个字节可以转换两个16进制数，不足两位的补0
            // aByte & 0xFF 还是 aByte 本身（一个字节最大值就是 0xFF）
            hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                des.append("0");
            }
            des.append(hex);
        }
        return des.toString();
    }

    /**
     * 为对象中有 @Encrypt注解的 String 属性加密
     * {@link Encrypt}
     *
     * @param object 对象
     */
    public static void encoderByAnno(Object object) {
        encryptHandler(object, true);
    }

    /**
     * 为对象中有 @Encrypt注解的 String 属性解密
     * {@link Encrypt}
     *
     * @param object 对象
     */
    public static void decoderByAnno(Object object) {
        encryptHandler(object, false);
    }

    private static void encryptHandler(Object object, boolean isEncoder) {
        if (object == null) {
            return;
        }
        Class<?> clazz = object.getClass();
        boolean presentType = clazz.isAnnotationPresent(Encrypt.class);
        Encrypt encrypt = clazz.getAnnotation(Encrypt.class);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 忽略 final 和 static 属性
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 类和属性都不存在 Trim 注解则跳过
            if (!field.isAnnotationPresent(Encrypt.class) && !presentType) {
                continue;
            }
            try {
                // 获取属性值并判断是否需要 trim
                Object attrValue = field.get(object);
                if (attrValue == null || attrValue.equals("")) {
                    continue;
                }
                if (attrValue instanceof String) {
                    Encrypt fieldEncrypt = field.getAnnotation(Encrypt.class);
                    encrypt = fieldEncrypt != null ? fieldEncrypt : encrypt;
                    String fieldName = field.getName();
                    char[] chars = fieldName.toCharArray();
                    chars[0] = (char) (chars[0] - 32);
                    // 为什么采用调用 setter 方法的方式而不是调用 Field#set：是为保证封装的完整性，setter 方法可以存在其他的操作
                    // 如：拼接前缀，后缀等等
                    String setMethodName = "set" + String.valueOf(chars);
                    Method method = clazz.getMethod(setMethodName, String.class);
                    Function<String, String> encoder = isEncoder ? encrypt.strategy().getEncrypt().getEncoder() :
                        encrypt.strategy().getEncrypt().getDecoder();
                    method.invoke(object, encoder.apply(attrValue.toString()));
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.warn("class: {}", object.getClass().getTypeName(), e);
            }
            field.setAccessible(false);
        }
    }
}
