package com.creativec.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
public  class TymkConfigurerAdapter implements WebMvcConfigurer {

    @Resource
    public TymkInterceptor tymkInterceptor;

    private String[] ignorUrls;

    /**
     * 解决前端跨域请求问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        init();
        registry.addInterceptor(tymkInterceptor).addPathPatterns("/**").excludePathPatterns(ignorUrls);
    }

    public void init() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("web-pattern.yaml")) {
            Constructor constructor = new Constructor(WebPattern.class);
            TypeDescription webPatternsDescription = new TypeDescription(WebPattern.class);
            webPatternsDescription.putListPropertyType("antPatterns", String.class);
            constructor.addTypeDescription(webPatternsDescription);
            Yaml yaml = new Yaml(constructor);
            WebPattern webPatterns = yaml.load(input);
            ignorUrls = webPatterns.getAntPatterns().toArray(new String[0]);
        } catch (Exception e) {
            log.error("parse web-pattern.yaml error", e);
        }
    }

    @Bean
    public Converter<String, Date> stringToDateConvert() {
        final String dateFormat = "yyyy-MM-dd HH:mm:ss";
        final String shortDateFormat = "yyyy-MM-dd";
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                try {
                    if (source.contains("-")) {
                        SimpleDateFormat formatter;
                        if (source.contains(":")) {
                            formatter = new SimpleDateFormat(dateFormat);
                        } else {
                            formatter = new SimpleDateFormat(shortDateFormat);
                        }

                        Date dtDate = formatter.parse(source);
                        return dtDate;
                    } else if (source.matches("^\\d+$")) {
                        Long lDate = new Long(source);
                        return new Date(lDate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        };
    }
}
