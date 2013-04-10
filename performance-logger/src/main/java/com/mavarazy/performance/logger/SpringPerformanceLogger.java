package com.mavarazy.performance.logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

import com.mavarazy.performance.flow.FlowSpecification;

public class SpringPerformanceLogger extends ProxyConfig implements BeanPostProcessor, BeanClassLoaderAware, Ordered {

    /**
     * Generated 20/10/2012
     */
    final static private long serialVersionUID = 5523207470940441716L;

    final private MethodInterceptor loggingAdvisor;

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    
    public SpringPerformanceLogger(FlowSpecification flowSpecification) {
        loggingAdvisor = new CallMethodInterceptor(new CallLogService(flowSpecification));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        assert targetClass != null : "Target class of the bean unclear";

        if (bean instanceof Advised) {
            ((Advised) bean).addAdvice(this.loggingAdvisor);
        } else {
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            // Copy our properties (proxyTargetClass etc) inherited from ProxyConfig.
            proxyFactory.copyFrom(this);
            proxyFactory.addAdvice(this.loggingAdvisor);
            bean = proxyFactory.getProxy(this.beanClassLoader);
        }

        return bean;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader != null ? classLoader : beanClassLoader;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
