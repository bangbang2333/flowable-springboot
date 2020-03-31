package com.creativec.common.config;

import com.creativec.common.base.ApplicationContextHolder;
import com.creativec.common.base.SerialNumberService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Configuration
public class ApplicationConfig {

    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //拷贝过程忽略null值属性
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public SerialNumberService serialNumberService() {
        SerialNumberService serialNumberService = new SerialNumberService();
        serialNumberService.setRedisTemplate(redisTemplate);
        return serialNumberService;
    }
}
