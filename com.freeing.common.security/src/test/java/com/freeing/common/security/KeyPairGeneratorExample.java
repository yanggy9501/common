package com.freeing.common.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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

    public static void testEnDe() {
        try {
            // 示例：假设这是之前生成的 Base64 编码的公钥和私钥（实际运行时替换为你的密钥）
            String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA...";  // 替换为实际公钥
            String privateKeyBase64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC...";  // 替换为实际私钥

            // 1. 从 Base64 加载公钥
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // 2. 从 Base64 加载私钥
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // 3. 明文
            String originalText = "Hello, RSA Encryption! 这是一个测试字符串。";

            // 4. 加密：使用公钥
            String encryptedText = encrypt(originalText, publicKey);
            System.out.println("加密后 (Base64): " + encryptedText);

            // 5. 解密：使用私钥
            String decryptedText = decrypt(encryptedText, privateKey);
            System.out.println("解密后: " + decryptedText);
            System.out.println("是否匹配原明文: " + originalText.equals(decryptedText));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 加密方法
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        // cipher:密码，暗号
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密方法
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, "UTF-8");
    }
}
