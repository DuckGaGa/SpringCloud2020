package com.itoyoung.springcloud.service;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.service.impl.PaymentFeignFallbackServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE", fallback = PaymentFeignFallbackServiceImpl.class)
//@RequestMapping("/payment")
public interface IPaymentFeignService {

    @GetMapping("/payment/get/{id}")
    public Result getById(@PathVariable("id") Integer id);

    @GetMapping("/payment/ok/get/{id}")
    public Result hystrixPaymentInfoOk(@PathVariable("id") Integer id);

    @GetMapping("/payment/timeout/get/{id}")
    public Result hystrixPaymentInfoTimeout(@PathVariable("id") Integer id);

    @GetMapping("/payment/timeout/serviceFallback/get/{id}")
    public Result hystrixPaymentInfoTimeoutOfServiceFallback(@PathVariable("id") Integer id);
}
