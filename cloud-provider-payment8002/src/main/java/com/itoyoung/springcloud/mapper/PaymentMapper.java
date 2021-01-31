package com.itoyoung.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itoyoung.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMapper extends BaseMapper<Payment> {

    Payment selectById(Integer id);
}
