package com.creativec.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;


@Data
public class PageResult<T> {
    private int current;

    private int size;

    private long total;

    private int pages;

    private List<T> list;

    public PageResult(int current, int size, int pages, long total, List<T> list) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }


    public static <T> PageResult ok(List<T> list) {
        PageInfo<T> info = new PageInfo<>(list);
        return new PageResult<>(info.getPageNum(), info.getPageSize(), info.getPages(), info.getTotal(), info.getList());
    }

    public static <T> PageResult ok(IPage<T> info) {
        return new PageResult<>((int) info.getCurrent(), (int) info.getSize(), (int) info.getPages(), info.getTotal(), info.getRecords());
    }
}