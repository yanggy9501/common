package com.freeing.common.support.observer;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 被观察者
 *
 * @author yanggy
 */
public abstract class Observable {
    /**
     * 被观察者的观察者列表
     */
    private final CopyOnWriteArrayList<AbstractObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * 添加观察者
     *
     * @param obs 观察者
     */
    public void addObserver(AbstractObserver obs) {
        this.observers.add(obs);
    }

    /**
     * 移除观察者
     *
     * @param obs 观察者
     */
    public void removeObserver(AbstractObserver obs) {
        this.observers.remove(obs);
    }

    /**
     * 同步通知所有观察者
     *
     * @param event 通知事件
     */
    public void notifyObservers(NotifyEvent event) {
        for (AbstractObserver observer : observers) {
            observer.update(event);
        }
    }
}
