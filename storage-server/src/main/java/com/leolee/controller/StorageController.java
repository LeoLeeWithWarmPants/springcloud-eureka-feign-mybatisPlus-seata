package com.leolee.controller;

import com.leolee.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @ClassName StorageController
 * @Description: TODO
 * @Author LeoLee
 * @Date 2020/11/12
 * @Version V1.0
 **/
@RestController
@RequestMapping(value = "/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    @RequestMapping("/decrease")
    public String decrease(@RequestParam("productId") Long productId,@RequestParam("count") Integer count){
        storageService.decrease(productId,count);
        return "Decrease storage success";
    }
}
