# 使用指南

1.   引入依赖

     ```xml
     <dependency>
         <groupId>com.freeing</groupId>
         <artifactId>com.freeing.common.log</artifactId>
         <version>1.0</version>
     </dependency>
     ```

2.   开启 aop：@EnableAspectJAutoProxy

3.   继承抽象切面类，并交给 spring

     如：抽象类提供了两个扩展，已经两个抽象方法用于获取操作者信息

     ```java
     @Aspect
     @Component
     public class MyLogAspect extends BaseLogAspect {
         @Override
         protected String getOperatorName() {
             return "Operator";
         }
     
         @Override
         protected String getOperatorId() {
             return "id:00000";
         }
     
         @Override
         protected void beforeProceed(OperationLog operationLog, Object[] args) {
             System.out.println("扩展：目标方法执行之前");
         }
     
         @Override
         protected void afterProceed(OperationLog operationLog, Object result) {
             System.out.println("扩展：目标方法执行之后");
         }
     }
     ```

4.   定义监听器

     目标方法完成之后或者异常，都会发送一个 LogEvent 事件，外部进行监听，如：

     ```java
     @Component
     public class MyLogListener extends BaseLogListener {
         @Override
         protected void apply(OperationLog operationLog) {
             System.out.println(operationLog);
         }
     }
     ```

     其他方式，注入 LogListener 的 Bean

     ```java
     public class LogListener {
         /**
          * 消费者函数，日记记录的工作交给lambda表达式来完成
          */
         private final Consumer<OperationLog> consumer;
     
         public LogListener(Consumer<OperationLog> consumer) {
             this.consumer = consumer;
         }
     
         @EventListener(LogEvent.class)
         public void saveLog(LogEvent event) {
             OperationLog optLog = (OperationLog) event.getSource();
             consumer.accept(optLog);
         }
     }
     ```

     