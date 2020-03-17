package com.creativec.example.controller;

import com.creativec.example.service.CandidateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@Api(tags = {"应聘者相关接口"})
public class CandidateController {

    @Autowired private CandidateService candidateService;

    @GetMapping("/test")
    public String tt() {
        return "888888";
    }

    @GetMapping("/test2")
    public String tt2() {
        return candidateService.tt2();
    }

    @GetMapping("/test3")
    public String tt3() {
        return candidateService.tt3();
    }

    @GetMapping("/test4")
    public void tt4() {

    }
}
