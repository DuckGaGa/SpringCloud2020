package com.itoyoung.springcloud.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.IPaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private IPaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        return result;
    }

    @GetMapping("/ok/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixPaymentFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Result hystrixPaymentInfoOk(@PathVariable("id") Integer id) {
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        result.setMessage("Hystrix: paymentInfoOk : threadName: " + Thread.currentThread().getName());
        return result;
    }

    @GetMapping("/timeout/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixPaymentFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public Result hystrixPaymentInfoTimeout(@PathVariable("id") Integer id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("time out");
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        result.setMessage("Hystrix: paymentInfoTimeout : threadName: " + Thread.currentThread().getName());
        return result;
    }

    @GetMapping("/timeout/serviceFallback/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixPaymentFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public Result hystrixPaymentInfoTimeoutOfServiceFallback(@PathVariable("id") Integer id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Service Fallback");
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        result.setMessage("Hystrix: paymentInfoTimeout : threadName: " + Thread.currentThread().getName());
        return result;
    }

    public Result hystrixPaymentFallback(Integer id) {
        return new Result().errorResult("服务端: 服务繁忙或异常，请稍后再试,ID: " + id);
    }
}
