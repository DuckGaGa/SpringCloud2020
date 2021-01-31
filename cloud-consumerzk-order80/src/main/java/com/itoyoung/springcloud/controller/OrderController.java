package com.itoyoung.springcloud.controller;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static String PAYMENT_URL = "http://cloud-payment-service/payment/";

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

    @GetMapping("/payment/zkTest")
    public Result zkTest() {
        return restTemplate.getForObject(PAYMENT_URL + "zkTest", Result.class);
    }
}
