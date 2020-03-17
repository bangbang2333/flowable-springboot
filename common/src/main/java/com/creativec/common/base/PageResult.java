package com.creativec.common.base;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;


@Data
public class PageResult<T> {
    private int pageNum;

    private int pageSize;

    private long total;

    private int pages;

    private List<T> list;

    private PageResult(int pageNum, int pageSize, int pages, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }


    public static <T> PageResult getPageResult(List<T> list) {
        PageInfo<T> info = new PageInfo<>(list);
        return new PageResult<T>(info.getPageNum(), info.getPageSize(), info.getPages(), info.getTotal(), info.getList());
    }
}