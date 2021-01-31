package com.itoyoung.springcloud.controller;

import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.IPaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private IPaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/addOrEdit")
    public Result addOrEdit(@RequestBody Payment payment) {
        Result result = new Result().defaultSuccess(paymentService.addOrEdit(payment));
        result.setCode(port + "-" + "200");
        return result;
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        return result;
    }
}
