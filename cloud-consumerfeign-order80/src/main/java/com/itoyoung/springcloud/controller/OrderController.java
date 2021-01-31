package com.itoyoung.springcloud.controller;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.IPaymentFeignService;
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

    @Resource
    private IPaymentFeignService paymentFeignService;

    @GetMapping("/payment/get/{id}")
    public Result getPaymentById(@PathVariable("id") Integer id) {
        return paymentFeignService.getById(id);
    }

    @GetMapping("/payment/timeout/get/{id}")
    public Result openFeignTimeout(@PathVariable("id") Integer id) {
        return paymentFeignService.openFeignTimeOut(id);
    }

    @PostMapping("/payment/create")
    public Result createPayment(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "addOrEdit", payment, Result.class);
    }
}
