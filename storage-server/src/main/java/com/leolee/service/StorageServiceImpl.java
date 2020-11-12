package com.leolee.service;

import com.leolee.dao.StorageDao;
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
@Service("storageService")
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    private StorageDao storageDao;

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long productId, Integer count) {
        LOGGER.info("------->扣减库存开始");
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        storageDao.decrease(productId,count);
        LOGGER.info("------->扣减库存结束");
    }
}
