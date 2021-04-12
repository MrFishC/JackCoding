package com.jack.a01_design_pattern.a01_dynamic_agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 14:23
 * @描述 动态代理
 *
 * method.invoke()和invoke()简单理解     https://blog.csdn.net/qq_34562093/article/details/84889499
 *
 * 动态代理的作用是什么：                  https://www.zhihu.com/question/20794107
 *
 *      Proxy类的代码量被固定下来，不会因为业务的逐渐庞大而庞大；
 *      可以实现AOP编程，实际上静态代理也可以实现，总的来说，AOP可以算作是代理模式的一个典型应用；
 *      解耦，通过参数就可以判断真实类，不需要事先实例化，更加灵活多变。
 *
 */
public class DynamicAgentCompany implements InvocationHandler {

    /*持有的真实对象*/
    private Object factory;

    public Object getFactory() {
        return factory;
    }

    public void setFactory(Object factory) {
        this.factory = factory;
    }

    /*通过Proxy获得动态代理对象*/
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                factory.getClass().getClassLoader(),            //类加载器
                factory.getClass().getInterfaces(),             //代理类需要实现的所有接口 (getInterfaces这种方法只能获得自己接口，不能获得父元素接口)
                this                                         //处理类，接口，必须进行实现类，一般采用匿名内部
        );
    }

    /**
     *
     * @param proxy     代理对象
     * @param method    代理对象当前执行的方法的描述对象（反射）
     * @param args      方法的实际参数
     * @return
     * @throws Throwable
     *
     * method.invoke(Object obj, Object... args) : obj,对象。 args：实际参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(factory,args);
    }

}
