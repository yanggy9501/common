package com.freeing.common.support.decorator;

/**
 * 抽象的装饰组件
 * 聚合被抽象组件并且继承抽象组件，重写方法以增强聚合对象的功能
 * 调用装饰器方法去增强主体的功能
 *
 * @author yanggy
 */
public abstract class AbstractDecorator extends AbstractComponent {

    /**
     * 聚合被装饰对象
     */
    private final AbstractComponent component;

    public AbstractDecorator(AbstractComponent component) {
        this.component = component;
    }

    @Override
    public void execute() {
        before();
        component.execute();
        after();
    }

    protected void before() {

    }

    protected void after() {

    }
}
