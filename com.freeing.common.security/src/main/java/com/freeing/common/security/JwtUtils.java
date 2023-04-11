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
import java.util.Objects;


/**
 * jwt 工具类
 *
 * @author yanggy
 */
public class JwtUtils {
    public static final String DEFUALT_PUBLIC_KEY = "pub.key";
    public static final String DEFUALT_PRIVATE_KEY = "pri.key";

    /**
     * rsa非对称加密获取公/私钥文件工具
     */
    private static final RsaKey RSA_KEY_HELPER = new RsaKey();

    /**
     * jwt header头部
     */
    private static final Map<String, Object> HEADER = new HashMap<>(2);
    static {
        HEADER.put("alg", SignatureAlgorithm.HS256.getValue());
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
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                // 自定义有效载荷
                .setClaims(body)
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
    public static String getClaim(String token, String claim, String pubKeyPath)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (token == null || "".equals(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath))
            .parseClaimsJws(token);
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
    public static Claims getClaims(String token, String pubKeyPath)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath))
            .parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * 判断 token 是否存在与有效
     * 私钥用于签名、公钥用于验签
     *
     * @param pubKeyPath 公钥路径
     * @return boolean
     */
    public static boolean checkToken(String token, String pubKeyPath) {
        if(token == null || Objects.equals("", token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * token 是否过期
     *
     * @param claims claims
     * @return boolean
     */
    public static boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }
}
