package com.common.support.decorator;

import com.freeing.common.support.decorator.AbstractComponent;
import com.freeing.common.support.decorator.AbstractDecorator;

/**
 * @author yanggy
 */
public class TestDecorator {
    public static void main(String[] args) {
        // 主体
        AbstractComponent subject = new AbstractComponent() {
            @Override
            public void execute() {
                System.out.println("执行主体方法");
            }
        };

        AbstractDecorator decorator = new AbstractDecorator(subject) {
            @Override
            protected void before() {
                System.out.println("执行之前");
            }

            @Override
            protected void after() {
                System.out.println("执行之后");
            }
        };
        decorator.execute();
    }
}
