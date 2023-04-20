package com.freeing.common.security;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rsa256
 * 私钥用于签名、公钥用于验签: 签名并不是为了保密，而是为了保证这个签名是由特定的某个人签名的，而不是被其它人伪造的签名
 * 公钥用于加密、私钥用于解密，这才能起到加密作用
 */
public class RsaKey {
    private static final String PUBLIC_KEY_PREFIX = "PublicKey_";
    private static final String PRIVATE_KEY_PREFIX = "PrivateKey_";

    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * 获取公钥,用于解析token
     *
     * @param filename 公钥文件名
     * @return PublicKey 公钥对象
     */
    public PublicKey getPublicKey(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = PUBLIC_KEY_PREFIX + filename;
        Object publicKey = cache.get(key);
        if (publicKey instanceof PublicKey) {
            return (PublicKey) publicKey;
        }
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(filename + " is not exist");
        }
        try (DataInputStream dis = new DataInputStream(resourceAsStream)) {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pk = kf.generatePublic(spec);
            cache.put(key, pk);
            return pk;
        }
    }

    /**
     * 获取密钥 用于生成token
     *
     * @param filename 私钥文件名
     * @return PrivateKey私钥对象
     */
    public PrivateKey getPrivateKey(String filename)
            throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String key = PRIVATE_KEY_PREFIX + filename;
        Object privateKey = cache.get(key);
        if (privateKey instanceof PrivateKey) {
            return (PrivateKey) privateKey;
        }

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(filename + " is not exist");
        }
        try (DataInputStream dis = new DataInputStream(resourceAsStream)) {
            byte[] keyBytes = new byte[resourceAsStream.available()];
            dis.readFully(keyBytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pk = kf.generatePrivate(spec);
            cache.put(key, pk);
            return pk;
        }
    }
}