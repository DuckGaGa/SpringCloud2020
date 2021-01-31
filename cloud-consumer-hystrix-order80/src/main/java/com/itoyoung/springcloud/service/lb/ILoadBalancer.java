package com.itoyoung.springcloud.service.lb;

import org.springframework.cloud.client.ServiceInstance;

public interface ILoadBalancer {

    ServiceInstance getInstance(String serviceId);
}
