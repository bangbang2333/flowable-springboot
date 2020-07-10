package com.creativec.oa;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.List;
import java.util.concurrent.TimeUnit;

@ComponentScans({@ComponentScan(value = "com.creativec")})
@MapperScan({"com.creativec.mapper"})
@SpringBootApplication
public class OaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaApplication.class, args);
    }

    @Bean(name = "permissionCache")
    public Cache<String, List<Integer>> initCartCache() {
        return Caffeine.newBuilder()
                .initialCapacity(500)
                .maximumSize(5000)
                .expireAfterAccess(3, TimeUnit.DAYS)
                .build();
    }

}
