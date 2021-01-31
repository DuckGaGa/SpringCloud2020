package com.itoyoung.springcloud.service;

import com.itoyoung.springcloud.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
@RequestMapping("/payment")
public interface IPaymentFeignService {

    @GetMapping("/get/{id}")
    Result getById(@PathVariable("id") Integer id);

    @GetMapping("/timeout/get/{id}")
    Result openFeignTimeOut(@PathVariable("id") Integer id);
}
