package com.freeing.common.support.observer;

import java.util.List;

/**
 * 被观察者
 *
 * @author yanggy
 */
public interface IObservable {
    /**
     * 获取被观察对象的观察者列表
     */
    default List<IObserver> getObservers() {
        throw new UnsupportedOperationException();
    }

    /**
     * 添加观察者
     *
     * @param obs 观察者
     */
    default void addObserver(IObserver obs) {
        this.getObservers().add(obs);
    }

    /**
     * 移除观察者
     *
     * @param obs 观察者
     */
    default void removeObserver(IObserver obs) {
        this.getObservers().remove(obs);
    }

    /**
     * 同步通知所有观察者
     *
     * @param event 通知事件
     */
    default void notifyObservers(NotifyEvent event) {
        for (IObserver observer : this.getObservers()) {
            observer.listen(event);
        }
    }
}
