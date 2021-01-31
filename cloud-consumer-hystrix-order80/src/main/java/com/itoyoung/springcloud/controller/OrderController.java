package com.itoyoung.springcloud.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.IPaymentFeignService;
import com.itoyoung.springcloud.service.lb.impl.MyRoundRobinLoadBalancer;
import com.netflix.discovery.util.StringUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@DefaultProperties(defaultFallback = "globalFallbackMethod")
public class OrderController {

    @Resource
    private IPaymentFeignService paymentFeignService;

    @GetMapping("/payment/get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        return paymentFeignService.getById(id);
    }


    @GetMapping("/payment/ok/{id}")
    @HystrixCommand(fallbackMethod = "hystrixOrderFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public Result hystrixPaymentInfoOk(@PathVariable("id") Integer id) {
        return paymentFeignService.hystrixPaymentInfoOk(id);
    }

    @GetMapping("/payment/timeout/{id}")
    @HystrixCommand(fallbackMethod = "hystrixOrderFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    public Result hystrixPaymentInfoTimeout(@PathVariable("id") Integer id) {
        return paymentFeignService.hystrixPaymentInfoTimeout(id);
    }

    @GetMapping("/payment/timeout/globalFallback/{id}")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    public Result paymentTimeoutGlobalFallback(@PathVariable("id") Integer id) {
        return paymentFeignService.hystrixPaymentInfoTimeout(id);
    }

    @GetMapping("/payment/timeout/serviceFallback/{id}")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    public Result paymentTimeoutServiceallback(@PathVariable("id") Integer id) {
        return paymentFeignService.hystrixPaymentInfoTimeoutOfServiceFallback(id);
    }

    public Result hystrixOrderFallback(Integer id) {
        return new Result().errorResult("客户端: 服务器繁忙或异常，请稍后再试，ID: " + id);
    }

    public Result globalFallbackMethod() {
        return new Result().errorResult("GlobalFallback:客户端: 服务器繁忙或异常，请稍后再试，Param: ");
    }

}
