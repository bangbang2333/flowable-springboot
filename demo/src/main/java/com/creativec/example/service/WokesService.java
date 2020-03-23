package com.creativec.example.service;

import com.creativec.common.base.BaseService;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.Wokes;

public interface WokesService extends BaseService<Wokes> {

    /**
     * 分页查询
     * @param wokes
     * */

    PageResult getWokesByPage(Wokes wokes);

    /**
     * 添加招聘信息
     * @param wokes
     * */
    String addWokes(Wokes wokes);

    /**
     * 修改招聘信息
     * @param wokes
     * */
    Boolean updateWokes(Wokes wokes);

    /**
     * 删除招聘信息
     * @param kid
     * */
    Boolean removeWokes(String kid);
}
