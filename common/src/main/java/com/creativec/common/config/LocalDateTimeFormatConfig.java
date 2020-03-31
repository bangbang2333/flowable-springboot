package com.creativec.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author ZSX
 */
@Configuration
public class LocalDateTimeFormatConfig {
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        om.registerModule(javaTimeModule);
        return om;
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public String print(LocalDate object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {
            @Override
            public String print(LocalDateTime object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }

    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<>() {
            @Override
            public String print(LocalTime object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            @Override
            public LocalTime parse(String text, Locale locale) {
                return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        };
    }

}
