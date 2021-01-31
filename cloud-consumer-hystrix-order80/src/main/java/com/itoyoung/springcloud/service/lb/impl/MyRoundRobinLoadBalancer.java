package com.itoyoung.springcloud.service.lb.impl;

import com.itoyoung.springcloud.service.lb.ILoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyRoundRobinLoadBalancer implements ILoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("************第几次访问: " + next);
        return next;
    }

    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance getInstance(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        int index = getAndIncrement() % instances.size();
        return instances.get(index);
    }
}
