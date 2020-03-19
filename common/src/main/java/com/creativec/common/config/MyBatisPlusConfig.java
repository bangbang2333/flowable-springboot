package com.creativec.common.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.creativec.common.base.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisMetaObjectHandler mybatisMetaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }

    public static class UUIDIdentifierGenerator extends DefaultIdentifierGenerator {
        @Override
        public String nextUUID(Object entity) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return new UUID(random.nextLong(), random.nextLong()).toString().toUpperCase();
        }
    }

    @Bean
    public IdentifierGenerator idGenerator() {
        return new UUIDIdentifierGenerator();
    }

    //分页插件
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        // paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        // paginationInterceptor.setLimit(500);
//        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return paginationInterceptor;
//    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public LogicSqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

}