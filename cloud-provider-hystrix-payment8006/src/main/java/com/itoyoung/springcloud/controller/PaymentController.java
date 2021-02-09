package com.itoyoung.springcloud.controller;

import cn.hutool.core.util.IdUtil;
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
import java.util.UUID;

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

    /**
     * 当10秒内，10个请求异常或超时导致服务降级次数超过6个时会直接触发服务熔断
     * 之后即使正常调用也会返回报错信息，直至之后多次请求成功一次后恢复
     *
     * @param id
     * @return
     */
    @GetMapping("/break/{id}/{millis}")
    @HystrixCommand(fallbackMethod = "hystrixPaymentFallback", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求次数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 统计失败率的时间窗口期
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            // 开路后的自我修复时间: 设置在回路被打开，拒绝请求到再次尝试请求并决定回路是否继续打开的时间
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public Result paymentCircuitBreaker(@PathVariable("id") Integer id, @PathVariable("millis") Long millis) {
        int i = 9 / id;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result().defaultSuccess("调用成功: result: " + i + " ID: " + id + " UUID:" + IdUtil.simpleUUID());
    }

    public Result hystrixPaymentFallback(Integer id, Long millis) {
        return new Result().errorResult("服务端: 服务繁忙或异常，请稍后再试,ID: " + id + " millis: " + millis);
    }

}
