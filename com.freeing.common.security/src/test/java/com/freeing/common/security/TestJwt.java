package com.freeing.common.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanggy
 */
public class TestJwt {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "kato");
        map.put("sex", "女");
        // 私钥用于签名、公钥用于验签
//        String token = JwtUtils2.generateToken(map, JwtUtils2.DEFAULT_PRIVATE_KEY, 9000);
//        System.out.println(token);
//        System.out.println(JwtUtils2.checkToken(token, JwtUtils2.DEFAULT_PUBLIC_KEY));
//
//        System.out.println(JwtUtils2.getClaims(token, JwtUtils2.DEFAULT_PUBLIC_KEY));
        String s = JwtUtils.generateToken(map, "123", 52000);
        System.out.println(s);
        System.out.println(JwtUtils.getClaims(s, "123"));

    }
}
