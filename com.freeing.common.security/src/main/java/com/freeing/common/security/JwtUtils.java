package com.freeing.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


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
     * @param expire 过期时间
     * @return token
     */
    public static String generateToken(Map<String, Object> body, String secret, long expire) {
        return Jwts.builder()
                .setHeader(HEADER)
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                // 自定义有效载荷
                .setClaims(body)
                // 签名
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
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
     */
    public static String getClaim(String token, String claim, String secret) {
        if (token == null || "".equals(token)) {
            return "";
        }
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
     * @return claim值
     */
    public static Claims getClaims(String token, String secret) {
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * 判断 token 是否存在与有效
     *
     * @param secret 密钥
     * @return boolean
     */
    public static boolean checkToken(String token, String secret) {
        if(token == null || Objects.equals("", token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
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
