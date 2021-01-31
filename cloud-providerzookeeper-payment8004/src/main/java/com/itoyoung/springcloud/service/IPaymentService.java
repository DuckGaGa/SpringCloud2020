package com.itoyoung.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itoyoung.springcloud.entities.Payment;

public interface IPaymentService extends IService<Payment> {
    Integer addOrEdit(Payment payment);

    Payment selectById(Integer Id);
}
