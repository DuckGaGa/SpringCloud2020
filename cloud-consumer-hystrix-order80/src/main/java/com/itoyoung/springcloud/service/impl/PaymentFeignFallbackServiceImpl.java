package com.itoyoung.springcloud.service.impl;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.service.IPaymentFeignService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFeignFallbackServiceImpl implements IPaymentFeignService {
    @Override
    public Result getById(Integer id) {
        return new Result().errorResult("FallbackService: getById: 服务器异常或繁忙，请稍后再试,ID: " + id);
    }

    @Override
    public Result hystrixPaymentInfoOk(Integer id) {
        return new Result().errorResult("FallbackService: hystrixPaymentInfoOk: 服务器异常或繁忙，请稍后再试,ID: " + id);
    }

    @Override
    public Result hystrixPaymentInfoTimeout(Integer id) {
        return new Result().errorResult("FallbackService: hystrixPaymentInfoTimeout: 服务器异常或繁忙，请稍后再试,ID: " + id);
    }

    @Override
    public Result hystrixPaymentInfoTimeoutOfServiceFallback(Integer id) {
        return new Result().errorResult("FallbackService: timeoutOfServiceFallback: 服务器异常或繁忙，请稍后再试,ID: " + id);
    }
}
