package com.freeing.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * jwt 工具类
 * RS256 非对称加密
 *
 * @author yanggy
 */
public class JwtUtils2 {
    public static final String DEFAULT_PUBLIC_KEY = "pub.key";
    public static final String DEFAULT_PRIVATE_KEY = "pri.key";

    /**
     * rsa非对称加密获取公/私钥文件工具
     */
    private static final RsaKey RSA_KEY_HELPER = new RsaKey();

    /**
     * jwt header头部
     */
    private static final Map<String, Object> HEADER = new HashMap<>(2);
    static {
        HEADER.put("alg", SignatureAlgorithm.RS256.getValue());
        HEADER.put("typ", "JWT");
    }

    /**
     * 生成 token (签名)
     * 私钥用于签名、公钥用于验签
     *
     * @param body 自定义有效载荷
     * @param priKeyPath 密钥文件名
     * @param expire 过期时间
     * @return token
     */
    public static String generateToken(Map<String, Object> body, String priKeyPath, long expire)
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return Jwts.builder()
                .setHeader(HEADER)
                // 自定义有效载荷
                .setClaims(body)
                // 过期时间，先调用setClaims，在调用setExpiration 否则过期时间会为null
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                // 签名
                .signWith(SignatureAlgorithm.RS256, RSA_KEY_HELPER.getPrivateKey(priKeyPath))
                // 合成
                .compact();
    }

    /**
     * 获取任意claim 的value值
     * 私钥用于签名、公钥用于验签
     *
     * @param token jwt令牌
     * @param claim claimName
     * @param pubKeyPath 公钥路径
     * @return claim值
     */
    public static String getClaim(String token, String claim, String pubKeyPath) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                .setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath))
                .parseClaimsJws(token);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        Claims body = claimsJws.getBody();
        return (String) body.get(claim);
    }

    /**
     * 获取任意claim 的value值
     * 私钥用于签名、公钥用于验签
     *
     * @param token jwt令牌
     * @param pubKeyPath 公钥路径
     * @return claim值
     */
    public static Map<String, Object> getClaims(String token, String pubKeyPath) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                .setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath))
                .parseClaimsJws(token);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return claimsJws.getBody();
    }
}
