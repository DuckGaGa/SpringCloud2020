package com.itoyoung.springcloud.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itoyoung.springcloud.common.Result;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.service.IPaymentService;
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

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("Service: " + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            System.out.println("ServiceId: " + instance.getServiceId() + " InstanceId: " + instance.getInstanceId() +
            " Host:port: "+ instance.getHost()  + " " + instance.getPort() + " uri: " + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/timeout/get/{id}")
    public Result openFeignTimeOut(@PathVariable("id") Integer id) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Result result = new Result().defaultSuccess(paymentService.selectById(id));
        result.setCode(port + "-" + "200");
        return result;
    }
}
