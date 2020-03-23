package com.creativec.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creativec.common.base.BaseServiceImpl;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.Wokes;
import com.creativec.example.mapper.WokesMapper;
import com.creativec.example.service.WokesService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author llpei
 * @Description:
 * @date Created in 14:28 2020/3/23
 */
@Service
public class WokesServiceImpl extends BaseServiceImpl<WokesMapper, Wokes> implements WokesService {

    /**
     * 分页查询招聘信息
     * */
    @Override
    public PageResult getWokesByPage(Wokes wokes) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",wokes.getName());
        PageHelper.startPage(wokes.getPageNum(),wokes.getPageSize());
        List list = super.list(wrapper);
        return PageResult.getPageResult(list);
    }
    /**
     * 添加招聘信息
     * */
    @Override
    public String addWokes(Wokes wokes) {
        String kid = super.insert(wokes);
        return kid;
    }

    /**
     * 修改招聘信息
     * */
    @Override
    public Boolean updateWokes(Wokes wokes) {
        return super.updateById(wokes);
    }

    @Override
    public Boolean removeWokes(String kid) {
        return super.removeById(kid);
    }
}
