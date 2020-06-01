package com.blog.core.base;

/**
 * 基础枚举接口
 * @version 1.0
 * @param <k>
 * @param <v>
 */
public interface BaseEnum<k,v> {

    /**
     *
     * 获取编码
     * @return 编码
     */
    k code();

    /**
     *
     * 获取描述
     *
     * @return 描述
     */
    v desc();

}
