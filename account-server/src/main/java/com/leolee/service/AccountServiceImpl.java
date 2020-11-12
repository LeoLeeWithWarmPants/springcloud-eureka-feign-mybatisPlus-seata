package com.leolee.service;

import com.leolee.dao.AccountDao;
import com.leolee.feign.OrderClient;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author IT云清
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OrderClient orderClient;

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long userId, BigDecimal money) throws Exception {
        LOGGER.info("------->扣减账户开始account中:" + money.intValue());
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        //模拟超时异常，全局事务回滚
//        if (money.intValue() == 10) {
//            throw new Exception();
//        }

        accountDao.decrease(userId,money);
        LOGGER.info("------->扣减账户结束account中");

        //修改订单状态，此调用会导致调用成环
        LOGGER.info("修改订单状态开始");
        String mes = orderClient.update(userId, money,0);
        LOGGER.info("修改订单状态结束：{}",mes);
    }
}
