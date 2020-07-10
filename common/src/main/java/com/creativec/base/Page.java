package com.creativec.base;

import lombok.Data;

@Data
public class Page {

    private Integer current = 1;

    private Integer size = 10;


    public void setCurrent(Integer current) {
        if (current != null) {
            this.current = current;
        }
    }


    public void setSize(Integer size) {
        if (size != null) {
            this.size = size;
        }
    }

    public com.baomidou.mybatisplus.extension.plugins.pagination.Page get() {
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, size, 0, true);
    }
}