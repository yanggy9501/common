package com.freeing.common.component.enumnew;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;

/**
 * 策略
 */
public enum EncryptStrategy {
    /**
     * BASE64
     */
    BASE64(new Encrypt(s -> new String(Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8))),
        s -> new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)))
    ));

    private final Encrypt encrypt;

    EncryptStrategy(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    public Encrypt getEncrypt() {
        return encrypt;
    }

    public static class Encrypt {
        private Function<String, String> encoder;

        private Function<String, String> decoder;

        public Encrypt(Function<String, String> encoder, Function<String, String> decoder) {
            this.encoder = encoder;
            this.decoder = decoder;
        }

        public Function<String, String> getEncoder() {
            return encoder;
        }

        public void setEncoder(Function<String, String> encoder) {
            this.encoder = encoder;
        }

        public Function<String, String> getDecoder() {
            return decoder;
        }

        public void setDecoder(Function<String, String> decoder) {
            this.decoder = decoder;
        }
    }
}