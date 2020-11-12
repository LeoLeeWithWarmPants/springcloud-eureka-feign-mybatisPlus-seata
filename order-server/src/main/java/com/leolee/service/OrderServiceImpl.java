package com.leolee.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leolee.dao.OrderDao;
import com.leolee.entity.Order;
import com.leolee.feign.AccountClient;
import com.leolee.feign.StorageClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private AccountClient accountClient;

    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    @DS("master")
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        LOGGER.info("------->交易开始");
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        //本地方法
        order.setStatus(0);
        orderDao.insert(order);

        //远程方法 扣减库存
        storageClient.decrease(order.getProductId(),order.getCount());

        //远程方法 扣减账户余额
        accountClient.decrease(order.getUserId(),order.getMoney());

        LOGGER.info("------->交易结束");
    }

    /**
     * 修改订单状态
     */
    @Override
    public void update(Long userId, BigDecimal money, Integer status) {
        LOGGER.info("修改订单状态，入参为：userId={},money={},status={}",userId,money,status);
        orderDao.update(userId,money,status);
    }
}
