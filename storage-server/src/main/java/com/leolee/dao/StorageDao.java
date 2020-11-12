package com.leolee.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leolee.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao extends BaseMapper<Storage> {

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
