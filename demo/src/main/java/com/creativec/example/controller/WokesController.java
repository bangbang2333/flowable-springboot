package com.creativec.example.controller;

import com.creativec.common.base.PageResult;
import com.creativec.example.entity.Wokes;
import com.creativec.example.service.WokesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author llpei
 * @Description:
 * @date Created in 14:49 2020/3/23
 */
@RestController
@RequestMapping("/wokes")
@Api(tags = "招聘信息表接口")
public class WokesController {

    @Autowired
    private WokesService wokesService;

    @ApiOperation(notes = "getWokes",value = "招聘信息列表")
    @GetMapping("/getWokes")
    public PageResult getWokes(Wokes wokes){
        return wokesService.getWokesByPage(wokes);
    }

    @ApiOperation(notes = "addWokes",value = "添加招聘信息")
    @PostMapping("/addWokes")
    public String addWokes(Wokes wokes){
        return wokesService.addWokes(wokes);
    }

    @ApiOperation(notes = "updateWokes",value = "修改招聘信息")
    @PostMapping("/updateWokes")
    public Boolean updateWokes(Wokes wokes){
        return wokesService.updateWokes(wokes);
    }

    @ApiOperation(notes = "removeWokes",value = "删除招聘信息")
    @DeleteMapping("/removeWokes")
    public Boolean removeWokes(String kid){
        return wokesService.removeWokes(kid);
    }
}
