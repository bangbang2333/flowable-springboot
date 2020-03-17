package com.creativec.common.base;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZSX
 */
public class BaseService<M extends BaseMyBatisMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> {

    @Autowired protected M mapper;

    /**
     * Wrapper查询单个数据
     */
    @TranRead
    public T selectOne(Wrapper<T> queryWrapper) {
        return this.mapper.selectOne(queryWrapper);
    }

    /**
     * 查询全部实体数量
     *
     * @return
     */
    @TranRead
    public Integer selectCount() {
        return this.mapper.selectCount(null);
    }

    /**
     * 通过主键查询
     */
    public T selectById(String id) {
        if (null == id) {
            return null;
        }
        return this.mapper.selectById(id);
    }


    /**
     * 通过主键查询
     */
    @TranRead
    public T selectByPrimaryKey(String id) {
        if (id == null) {
            return null;
        }
        return this.mapper.selectById(id);
    }

    /**
     * 按照主键进行删除
     *
     * @param key
     * @return
     */
//    @TranSave
//    public boolean removeById(String kid) {
//        if (kid == null) {
//            return false;
//        }
//        BaseEntity entity = new BaseEntity();
//        entity.setKid(kid);
//        entity.setDeleted(null);
//        return updateById(t);
//    }


    /**
     * 按照实体进行修改
     *
     * @param record
     * @return
     */
    @Override
    public boolean updateById(T record) {
        return mapper.updateById(record) > 0;
    }

    /**
     * 全部添加
     */
    public String insert(T record) {
        if (mapper.insert(record) > 0) {
            return record.getKid();
        }
        return null;
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