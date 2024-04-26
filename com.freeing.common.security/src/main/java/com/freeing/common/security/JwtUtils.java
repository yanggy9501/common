package com.freeing.common.security;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * jwt 工具类
 * HS256 对称加密
 *
 * @author yanggy
 */
public class JwtUtils {

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
     *
     * @param body 自定义有效载荷
     * @param secret 密钥
     * @param expire 过期时间，单位秒
     * @return token
     */
    public static String generateToken(Map<String, Object> body, String secret, long expire) {
        return Jwts.builder()
                .setHeader(HEADER)
                // 自定义有效载荷
                .setClaims(body)
                // 过期时间，先调用setClaims，在调用setExpiration 否则过期时间会为null
                .setExpiration(new Date(System.currentTimeMillis() + (expire * 1000)))
                // 签名
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                // 压缩
                .compressWith(CompressionCodecs.GZIP)
                // 合成
                .compact();
    }

    /**
     * 获取任意claim 的value值
     *
     * @param token jwt令牌
     * @param claim claimName
     * @param secret 密钥
     * @return claim值
     * @throws Exception|ExpiredJwtException 解析失败，token 过期
     */
    public static String getClaim(String token, String claim, String secret) {
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return (String) body.get(claim);
    }

    /**
     * 获取任意claim 的value值
     *
     * @param token jwt令牌
     * @param secret 密钥
     * @return Claims|Map<String, Object>
     * @throws Exception|ExpiredJwtException 解析失败，token 过期
     */
    public static Map<String, Object> getClaims(String token, String secret) {
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token);
        return claimsJws.getBody();
    }
}
