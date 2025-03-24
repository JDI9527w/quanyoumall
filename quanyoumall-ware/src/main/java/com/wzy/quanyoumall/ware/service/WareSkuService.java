package com.wzy.quanyoumall.ware.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.common.exception.OutOfStockException;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskDetailEntity;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import com.wzy.quanyoumall.ware.vo.FareVo;
import com.wzy.quanyoumall.ware.vo.WareSkuLockVo;

import java.util.List;

/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    Page<WareSkuEntity> queryPage(WareSkuEntity wareSkuEntity, Page<WareSkuEntity> page);

    /**
     * 根据skuIds查询对应商品总库存
     *
     * @param skuIds
     * @return
     */
    List<SkuStockTO> getStokeBySkuIds(List<Long> skuIds);

    /**
     * 根据skuIds查询对应商品是否有库存
     *
     * @param skuIds
     * @return
     */
    List<SkuStockTO> getHasStokeBySkuIds(List<Long> skuIds);

    /**
     * 通过收货地址获取运费
     *
     * @param addrId
     * @return
     */
    FareVo getFareByAddrId(Long addrId);

    /**
     * 根据订单信息锁定库存
     *
     * @param lockVo
     * @return
     * @throws OutOfStockException
     */
    boolean lockStockByLockVo(WareSkuLockVo lockVo) throws OutOfStockException;

    /**
     * 根据参数查询符合条件的仓库id
     *
     * @param skuId
     * @param num
     * @return
     */
    List<Long> listGetWareSkuIdByArgs(Long skuId, Integer num);

    /**
     * 根据库存工作单详情,回滚库存
     *
     * @param wareOrderTaskDetailEntity
     */
    int rollbackSingle(WareOrderTaskDetailEntity wareOrderTaskDetailEntity);


    /**
     * 释放工作单锁定的库存
     *
     * @param taskDetailEntityList
     */
    void rollbackStock(List<WareOrderTaskDetailEntity> taskDetailEntityList);
}

