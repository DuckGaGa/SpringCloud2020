package com.itoyoung.springcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itoyoung.springcloud.entities.Payment;
import com.itoyoung.springcloud.mapper.PaymentMapper;
import com.itoyoung.springcloud.service.IPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    @Override
    public Integer addOrEdit(Payment payment) {
        return payment.getId() == null ? paymentMapper.insert(payment) :
                paymentMapper.updateById(payment);
    }

    @Override
    public Payment selectById(Integer Id) {
        return paymentMapper.selectById(Id);
    }
}
