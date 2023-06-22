package com.freeing.common.support.pubsub;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 发布订阅的中心
 *
 * @author yanggy
 */
public class SubscribePublish {
    /**
     * 订阅者map，key为消息类型
     */
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<ISubscriber>> subscriberMap;

    /**
     * 创建线程池:
     * 核心线程数：计算机内核数 / 2
     * 最大线程数：计算机内核数
     * 空闲时间：60s，超过60s超过核心线程数的空闲线程被杀死
     * 任务队列长度：200
     * 线程池工厂：使用了jdk默认工厂
     * handler（队列满时的任务拒绝策略）：让提交任务的线程去执行
     */
    private final ExecutorService threadPool;

    /**
     * 本类实例
     */
    private static volatile SubscribePublish SUBSCRIBE_PUBLISH;

    private SubscribePublish() {
        subscriberMap = new ConcurrentHashMap<>(16);
        // 获取计算机有几个逻辑处理器，如果是8核16线程则返回16
        int processors = Runtime.getRuntime().availableProcessors();
        threadPool = new ThreadPoolExecutor(
            processors >> 1,
            processors,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(processors * 200),
            new DefaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
            namePrefix = "SubscribePublish-pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    /**
     * 注册订阅者
     *
     * @param subscriber 订阅对象
     */
    public void subscribe(ISubscriber subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Fail to subscribe! subscriber can not null.");
        }
        // 获取消息类型
        String messageType = subscriber.getMessageType();
        if (messageType == null) {
            throw new IllegalArgumentException("Fail to subscribe! Message type can not null.");
        }
        // 绑定订阅对象
        if (subscriberMap.get(messageType) == null) {
            // synchronized 是必要的，if 代码块的代码并不是原子操作
            synchronized (this) {
                if (subscriberMap.get(messageType) == null) {
                    CopyOnWriteArrayList<ISubscriber> subscribers = new CopyOnWriteArrayList<>();
                    subscribers.add(subscriber);
                    subscriberMap.put(messageType, subscribers);
                }
            }
        } else {
            subscriberMap.get(messageType).add(subscriber);
        }
    }

    /**
     * 退订
     *
     * @param subscriber 订阅对象
     */
    public void unSubscribe(ISubscriber subscriber) {
        if (subscriber != null && subscriber.getMessageType() != null) {
            CopyOnWriteArrayList<ISubscriber> subscribers = subscriberMap.get(subscriber.getMessageType());
            if (subscribers != null) {
                subscribers.remove(subscriber);
            }
        }
    }

    /**
     * 生产同步消息
     *
     * @param messageType 消息类型
     * @param message 消息
     */
    public void syncPublishMessage(String messageType, Object message) {
        sendMessage(messageType, message);
    }

    private void sendMessage(String messageType, Object message) {
        if (!subscriberMap.containsKey(messageType)) {
            return;
        }
        subscriberMap.get(messageType).forEach(subscriber -> subscriber.receiveMessage(message));
    }

    /**
     * 生产异步消息P
     *
     * @param messageType 消息类型
     * @param message     消息
     */
    public void asyncPublishMessage(String messageType, Object message) {
        if (!subscriberMap.containsKey(messageType)) {
            return;
        }
        /*
         * 此处把 for 循环写在threadPool.submit方法外面代表每个订阅者对消息的处理都是由一个线程执行
         * 如果把 for 循环写在threadPool.submit方法里面则代表一组相同类型的订阅者对同一个消息的处理是由一个线程处理
         */
        subscriberMap.get(messageType).forEach(subscriber ->
            this.threadPool.execute(() -> subscriber.receiveMessage(message))
        );
    }

    /**
     * 获取本类实例
     *
     * @return SubscribePublish对象
     */
    public static SubscribePublish getInstance() {
        if (SUBSCRIBE_PUBLISH == null) {
            synchronized (SubscribePublish.class) {
                // 再次检查，防止进来的时候单例刚好创建完
                if (SUBSCRIBE_PUBLISH == null) {
                    SUBSCRIBE_PUBLISH = new SubscribePublish();
                }
            }
        }
        return SUBSCRIBE_PUBLISH;
    }
}

