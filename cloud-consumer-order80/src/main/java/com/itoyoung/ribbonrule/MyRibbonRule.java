package com.itoyoung.ribbonrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MyRibbonRule {
    @Bean
    public IRule randomRule(){
        return new RandomRule();
    }
}
