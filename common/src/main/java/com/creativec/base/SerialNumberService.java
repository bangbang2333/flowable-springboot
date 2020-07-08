package com.creativec.base;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.util.Date;

import static java.time.temporal.ChronoField.*;

/**
 * 业务流水号服务接口
 */
@Slf4j
public class SerialNumberService {

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();

//    private static final DateTimeFormatter LOCAL_DATETIME_FORMATTER = new DateTimeFormatterBuilder()
//            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
//            .appendValue(MONTH_OF_YEAR, 2)
//            .appendValue(DAY_OF_MONTH, 2)
//            .appendValue(HOUR_OF_DAY, 2)
//            .appendValue(MINUTE_OF_HOUR, 2)
//            .appendValue(SECOND_OF_MINUTE, 2)
//            .toFormatter();

    @Autowired
    @Setter
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 定义流水号工单默认前缀
     */
    private static final String SERIAL_NUMBER = "serial:generator:";

    /**
     * 生成业务流水单号
     *
     * @param serialNumber  流水号参数
     * @param autoIncrement 是否自动自增。false需要后续调用increment方法，一般用于创建数据界面预先生成显示流水号；true一般用于程序处理过程直接生成单号。
     * @return
     */
    public String next(SerialNumber serialNumber, boolean autoIncrement) {
        Assert.isTrue(StringUtils.isNotBlank(serialNumber.getBizCode()), "流水号业务类型不能为空");
        LocalDateTime now = LocalDateTime.now();
        //构造redis的key
        String key = SERIAL_NUMBER + ":" + serialNumber.getBizCode();
        //判断key是否存在
        Boolean exists = redisTemplate.hasKey(key);

        Long incr = 1L;
        if (autoIncrement) {
            incr = redisTemplate.opsForValue().increment(key, 1);
            if (incr <= 0 || incr >= Math.pow(10, serialNumber.getSerialNumLength())) {
                //如果溢出，则重新从1开始循环
                log.debug("Resetting Redis serial number to " + incr + " of " + key);
                incr = 1L;
                redisTemplate.opsForValue().set(key, incr);
            }
        } else {
            Number num = (Number) redisTemplate.opsForValue().get(key);
            incr = num == null ? 0L : num.longValue();
            incr = incr + 1;
        }

        //设置过期时间
        if (!exists) {
            //构造redis过期时间 UnixMillis
            //设置过期时间为当天的最后一秒
            LocalTime time = LocalTime.of(23, 59, 59);
            LocalDateTime expireAt = LocalDateTime.of(now.toLocalDate(), time);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = expireAt.atZone(zone).toInstant();
            redisTemplate.expireAt(key, Date.from(instant));
        }

        //默认编码需要5位，位数不够前面补0
        String formattedNum = String.format("%0" + serialNumber.getSerialNumLength() + "d", incr);
        StringBuilder sb = new StringBuilder(20);
        //获取当前时间,返回格式字符串
        String date = now.format(LOCAL_DATE_FORMATTER);
        //转换成业务需要的格式  bizCode + date + incr
        sb.append(serialNumber.getBizCode()).append(date).append(formattedNum);

        return sb.toString();
    }

    /**
     * 对于next方法中autoIncrement=false的调用，需要在诸如数据提交成功后调用递增流水号
     *
     * @param serialNumber
     */
    public void increment(SerialNumber serialNumber) {
        String key = SERIAL_NUMBER + ":" + serialNumber.getBizCode();
        Long incr = redisTemplate.opsForValue().increment(key, 1);
        if (incr <= 0 || incr >= Math.pow(10, serialNumber.getSerialNumLength())) {
            //如果溢出，则重新从1开始循环
            log.debug("Resetting Redis serial number to " + incr + " of " + key);
            incr = 1L;
            redisTemplate.opsForValue().set(key, incr);
        }
    }

    public interface SerialNumber {
        String getBizCode();

        int getSerialNumLength();
    }
}
