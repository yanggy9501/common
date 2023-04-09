package com.freeing.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要工具
 *
 * @author yanggy
 */
public class EncryptUtils {

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
}
