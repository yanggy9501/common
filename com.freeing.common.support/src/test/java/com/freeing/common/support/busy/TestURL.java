package com.freeing.common.support.busy;

/**
 * @author yanggy
 */
public class TestURL {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        URL url = new URL("http", "127.0.0.1", 80, "/user/getid");
        url = url.addParameter("v", "1.3")
            .addParameter("type", "net")
            .addParameter("timestamp", System.currentTimeMillis())
            .addParameter("key", 4555544);

        System.out.println(url.toParameterString());
        System.out.println(url.toJavaURL());
    }
}
