package com.freeing.common.support.reflection;

/**
 * @author yanggy
 */
public class TestReflection {

    public static void main(String[] args) {
        Reflector reflector = new Reflector(User.class);
        User user = new User();
        user.setName("name");
        user.setGender("女");
        System.out.println(user);
    }
}
