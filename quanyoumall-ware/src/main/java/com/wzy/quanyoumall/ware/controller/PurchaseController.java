package com.wzy.quanyoumall.ware.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.Dto.PurchaseMergeDTO;
import com.wzy.quanyoumall.ware.entity.PurchaseEntity;
import com.wzy.quanyoumall.ware.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 采购信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R queryPageList(PurchaseEntity purchaseEntity,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        Page<PurchaseEntity> page = new Page<>(pageNum, pageSize);
        Page<PurchaseEntity> result = purchaseService.queryPage(purchaseEntity, page);
        return R.ok().put("data", result);
    }

    /**
     * 根据条件查询采购单
     *
     * @return
     */
    @GetMapping("/unreceive/list")
    public R queryListByCondition(@RequestParam String checkStatus) {
        List<PurchaseEntity> purchaseEntities = purchaseService.queryListByCondition(checkStatus);
        return R.ok().put("data", purchaseEntities);
    }

    //TODO:待测试.
    @PostMapping("/merge")
    public R mergePurchaseDetail(@RequestBody PurchaseMergeDTO purchaseMergeDTO) {
        purchaseService.contactPurchaseDetail(purchaseMergeDTO.getPurchaseDetailIdList(), purchaseMergeDTO.getPurchaseId());
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase) {
        purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase) {
        purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
