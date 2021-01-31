package com.itoyoung.springcloud.controller;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.lb.impl.MyRoundRobinLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment/";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/get/{id}")
    public Result getPaymentById(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(PAYMENT_URL + "get/" + id, Result.class);
    }

    @PostMapping("/payment/create")
    public Result createPayment(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "addOrEdit", payment, Result.class);
    }

    @Resource
    private MyRoundRobinLoadBalancer myRoundRobinLoadBalancer;

    /**
     * 自写的轮询算法
     *
     * @param id
     * @return
     */
    @GetMapping("/payment/mylb/get/{id}")
    public Result getPaymentByIdOfMyLb(@PathVariable("id") Integer id) {
        ServiceInstance instance = myRoundRobinLoadBalancer.getInstance("CLOUD-PAYMENT-SERVICE");
        System.out.println(instance.getUri());
        return restTemplate.getForObject(instance.getUri() + "/payment/get/" + id, Result.class);
    }
}
