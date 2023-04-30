package com.freeing.common.component.utils;

import com.freeing.common.component.annotation.Encrypt;
import com.freeing.common.component.annotation.Trim;
import com.freeing.common.security.enumnew.EncryptStrategy;

/**
 * @author yanggy
 */
@Encrypt(strategy = EncryptStrategy.BASE64)
public class User {
    @Trim
    private String username;

    private Integer age;

    @Trim
    private String love;

    public User() {

    }

    public User(String username, Integer age, String love) {
        this.username = username;
        this.age = age;
        this.love = love;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    @Override
    public String toString() {
        return "User{" +
            "username='" + username + '\'' +
            ", age=" + age +
            ", love='" + love + '\'' +
            '}';
    }
}
