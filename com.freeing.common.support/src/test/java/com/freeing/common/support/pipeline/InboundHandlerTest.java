package com.freeing.common.support.pipeline;

import com.freeing.common.support.pipeline.core.DefaultProcessPipeline;
import com.freeing.common.support.pipeline.core.ProcessHandlerContext;
import com.freeing.common.support.pipeline.core.ProcessPipeline;
import com.freeing.common.support.pipeline.core.handler.SimpleInboundHandler;

public class InboundHandlerTest {

    public static void main(String[] args) {
        ProcessPipeline pipeline = new DefaultProcessPipeline()
            .addLast("handle1", new SimpleInboundHandler<Apple>() {
                @Override
                public boolean beforeHandle(ProcessHandlerContext ctx, Apple inputParams) throws Exception {
                    return Boolean.TRUE;
                }

                @Override
                public void handle(ProcessHandlerContext ctx, Apple inputParams) throws Exception {
                    String color = inputParams.getColor();
                    System.out.println("name: " + inputParams.getName() + "===>" + color);
                    Pick pick = new Pick();
                    pick.setColor("blue");

                    // 执行下一个 handler
                    ctx.fireProcess(pick);
                }
            })
            .addLast("handle2", new SimpleInboundHandler<Pick>() {

                @Override
                public void handle(ProcessHandlerContext ctx, Pick inputParams) throws Exception {
                    System.out.println(ctx.name());
                    System.out.println(inputParams.getColor());

                    ctx.fireProcess(inputParams);
                }

            })
            .addLast("handle3", new SimpleInboundHandler<Apple>() {

                @Override
                public void handle(ProcessHandlerContext ctx, Apple inputParams) throws Exception {
                    System.out.println(ctx.name());
                    System.out.println(inputParams.getColor());
                    ctx.fireProcess(inputParams);
                }

                @Override
                public void exceptionCaught(ProcessHandlerContext ctx, Throwable cause) {
                    System.out.println("you are error =====> " + cause.toString());
                    System.out.println(cause);
                }
            });

        Apple apple = new Apple();
        apple.setColor("red");
        apple.setName("xieDi");
        pipeline.fireProcess(apple);


    }


    public static class Apple {
        String name;
        String color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class Pick {
        String name;
        String color;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
