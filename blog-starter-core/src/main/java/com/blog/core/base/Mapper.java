package com.blog.core.base;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 *
 * BaseMapper
 * @param <T>
 */
public interface Mapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
    @Override
    T selectOne(T t);

    @Override
    List<T> select(T t);

    @Override
    List<T> selectAll();

    @Override
    int selectCount(T t);

    @Override
    T selectByPrimaryKey(Object o);

    @Override
    boolean existsWithPrimaryKey(Object o);

    @Override
    int insert(T t);

    @Override
    int insertSelective(T t);

    @Override
    int updateByPrimaryKey(T t);

    @Override
    int updateByPrimaryKeySelective(T t);

    @Override
    int delete(T t);

    @Override
    int deleteByPrimaryKey(Object o);

    @Override
    List<T> selectByCondition(Object o);

    @Override
    int selectCountByCondition(Object o);

    @Override
    int deleteByCondition(Object o);

    @Override
    int updateByCondition(T t, Object o);

    @Override
    int updateByConditionSelective(T t, Object o);

    @Override
    List<T> selectByIds(String s);

    @Override
    int deleteByIds(String s);

    @Override
    int insertList(List<? extends T> list);
}
