package com.freeing.common.security;

import org.junit.Test;

/**
 * @author yanggy
 */
public class TestEncrypt {

    @Test
    public void base64() {
        String s = "123456";
        String s1 = EncryptUtils.encryptByBase64(s);
        System.out.println(s1);
        System.out.println(EncryptUtils.decryptByBase64(s1));
    }
}
