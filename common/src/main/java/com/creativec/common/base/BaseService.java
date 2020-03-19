package com.creativec.common.base;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;

/**
 * @author ZSX
 */
public class BaseService<M extends BaseMyBatisMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> {

    @Autowired public M mapper;


    /**
     * 通过主键查询
     */
    @Override
    public T getById(Serializable id) {
        if (null == id) {
            return null;
        }
        return this.mapper.selectById(id);
    }


    /**
     * 按照实体进行修改
     *
     * @param record
     * @return
     */
    @Override
    public boolean updateById(T record) {
        return this.mapper.updateById(record) > 0;
    }

    /**
     * 全部添加
     */
    public String insert(T record) {
        return this.mapper.insert(record) > 0 ? record.getKid() : null;
    }

    public boolean removeById(String kid) {
        BaseEntity entity = new BaseEntity();
        entity.setKid(kid);
        entity.setUpdatedAt(Long.valueOf(System.currentTimeMillis() / 1000).intValue());
        AuthDataHolder.User user = AuthDataHolder.get();
        entity.setUpdatedBy(user.getKid());
        entity.setUpdatedIp(user.getIp());
        return this.mapper.deleteByIdWithFill(entity) > 0;
    }

}