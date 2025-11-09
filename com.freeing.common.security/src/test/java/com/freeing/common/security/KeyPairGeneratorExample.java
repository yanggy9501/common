package com.freeing.common.security;

import java.security.*;
import java.util.Base64;

public class KeyPairGeneratorExample {
    public static void main(String[] args) {
        try {
            // 1. 创建密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            // 2. 初始化生成器，指定密钥长度为 2048 位
            keyPairGenerator.initialize(2048);

            // 3. 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // 4. 获取公钥和私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 5. 将密钥编码为 Base64 字符串
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // 6. 输出密钥
            System.out.println("公钥 (Base64):");
            System.out.println(publicKeyBase64);
            System.out.println("\n私钥 (Base64):");
            System.out.println(privateKeyBase64);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
