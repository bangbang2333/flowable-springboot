package com.creativec.base;

import lombok.Data;

@Data
public class Page {

    private Integer pageNum = 1;

    private Integer pageSize = 20;


    public void setPageNum(Integer pageNum) {
        if (pageNum != null) {
            this.pageNum = pageNum;
        }
    }


    public void setPageSize(Integer pageSize) {
        if (pageSize != null) {
            this.pageSize = pageSize;
        }
    }
}