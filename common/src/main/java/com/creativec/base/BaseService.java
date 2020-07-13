/**
 *
 */
package com.creativec.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creativec.entity.BaseTreeEntity;
import com.creativec.exception.BusinessException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cc
 *
 *         2020年3月21日
 */

public class BaseService<M extends BaseMapper<T>, T extends BaseTreeEntity> extends ServiceImpl<M, T> {
    public List<Integer> removeTree(Serializable id) {
        T t = getById(id);
        if (t == null) {
            throw new BusinessException("ID不存在");
        }
        LambdaUpdateWrapper<T> wrapper = new LambdaUpdateWrapper<T>().eq(T::getId, id).or().like(T::getParentId, "/" + id + "/");
        List<Integer> ids = list(wrapper).stream().map(T::getId).collect(Collectors.toList());
        if (removeByIds(ids) && t.getParentId() != -1) {
            int count = count(new LambdaQueryWrapper<T>().eq(T::getParentId, t.getParentId()));
            if (count == 0) {
                update(new LambdaUpdateWrapper<T>().eq(T::getId, t.getParentId()).set(T::getIsLeaf, true).set(T::getLevel, 1));
            }
        }
        if (ids.size() == 0) {
            throw new BusinessException("删除失败");
        }
        return ids;
    }

    public boolean saveOrUpdateTree(T t) {
        if (t.getId() == null) {
            t.setIsLeaf(true);
            if (t.getParentId() == null || t.getParentId() == -1) {
                t.setLevel(1);
                t.setParentId(-1);
                t.setParentIdPath("/");
                return save(t);
            }
            T parent = getById(t.getParentId());
            if (parent.getIsLeaf()) {
                parent.setIsLeaf(false);
                updateById(parent);
            }
            t.setLevel(parent.getLevel() + 1);
            t.setParentIdPath(parent.getParentIdPath() + parent.getId() + "/");
            return save(t);
        }
        return updateById(t);
    }

    public List<T> toCheckedTree(List<T> menus, Function<? super T, ? extends T> mapper) {
        if (menus.size() == 0) {
            return menus;
        }
        Map<Integer, List<T>> parent = menus.stream().map(mapper).collect(Collectors.groupingBy(T::getParentId));
        return initChild(parent.get(-1), parent);
    }

    public List<T> toTree(List<T> menus) {
        if (menus.size() == 0) {
            return menus;
        }
        Map<Integer, List<T>> parent = menus.stream().collect(Collectors.groupingBy(T::getParentId));
        return initChild(parent.get(-1), parent);
    }

    public List<T> initChild(List<T> menus, Map<Integer, List<T>> parent) {
        for (T t : menus) {
            List<T> child = parent.get(t.getId());
            if (child != null) {
                t.setChild(child);
                initChild(child, parent);
            }
        }
        return menus;
    }
}
