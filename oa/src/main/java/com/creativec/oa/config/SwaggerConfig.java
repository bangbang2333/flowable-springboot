package com.creativec.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSX
 */
@Configuration
@EnableSwagger2
@Profile({"local", "dev", "test"})
public class SwaggerConfig {

    @Bean
    public Docket createAdminApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //页面标题
                .title("demo RESTful API")
                //创建人
                .contact(new Contact("管理员", "", "service@demo.tech"))
                //版本号
                .version("1.0.0")
                //描述
                .description("demo相关接口")
                .build();

        //添加header参数
        List<Parameter> pars = new ArrayList<>();
        pars.add(buildParameter("Authorization", "传递token"));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.creativec.oa.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .groupName("内部接口");
    }


    private Parameter buildParameter(String name, String desc) {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(name)
                .description(desc)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .order(100)
                .build();
        return tokenPar.build();
    }
}
