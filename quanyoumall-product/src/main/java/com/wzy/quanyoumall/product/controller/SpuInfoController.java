package com.wzy.quanyoumall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.SpuInfoEntity;
import com.wzy.quanyoumall.product.service.SpuInfoService;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * spu信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R queryPageList(SpuInfoEntity spuInfoEntity,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        Page<SpuInfoEntity> page = new Page<>(pageNum, pageSize);
        Page<SpuInfoEntity> spuInfoEntityPage = spuInfoService.queryPage(spuInfoEntity, page);
        return R.ok().put("data", spuInfoEntityPage);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);
        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuSaveVo spuSaveVo) {
        spuInfoService.saveBySpuSaveVo(spuSaveVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 商品上架
     *
     * @param spuId
     * @return
     */
    @PostMapping("{spuId}/up")
    public R upSpuById(@PathVariable("spuId") Long spuId) {
        spuInfoService.upSpuById(spuId);
        return R.ok();
    }
}
