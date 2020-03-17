package com.creativec.example.service;

import com.creativec.common.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    public String tt2() {
        int i = 10 / 0;
        return "测试系统异常";
    }

    public String tt3() {
        throw new BusinessException("测试自定义异常");
    }
}
