package com.leolee.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leolee.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 创建订单
     * @param order
     * @return
     */
    void create(Order order);

    /**
     * 修改订单金额
     * @param userId
     * @param money
     */
    void update(@Param("userId") Long userId, @Param("money") BigDecimal money, @Param("status") Integer status);
}
