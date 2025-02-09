package com.wzy.quanyoumall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.constant.ProductConstant;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.ProductAttrValueEntity;
import com.wzy.quanyoumall.product.service.AttrService;
import com.wzy.quanyoumall.product.service.ProductAttrValueService;
import com.wzy.quanyoumall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品属性
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表查询
     *
     * @param attrEntity 查询参数封装对象
     * @param selectType 查询类型
     * @param pageNum    页码
     * @param pageSize   页大小
     * @return
     */
    @GetMapping("/{selectType}/list")
    public R list(AttrEntity attrEntity,
                  @PathVariable String selectType,
                  @RequestParam Integer pageNum,
                  @RequestParam Integer pageSize) {
        Page<AttrEntity> page = new Page<>(pageNum, pageSize);
        Integer selectAttrType = null;
        if (selectType.equals("base")) {
            selectAttrType = ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode();
        } else if (selectType.equals("sale")) {
            selectAttrType = ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode();
        }
        Page<AttrVo> result = attrService.queryPage(page, attrEntity, selectAttrType);
        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrVo av = attrService.getDetailById(attrId);
        return R.ok().put("data", av);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAndThen(attrVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrVo attrVo) {
        attrService.updateByIdAndThen(attrVo);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> attrIds) {
        attrService.removeByIdsAndThen(attrIds);
        return R.ok();
    }

    @GetMapping("/sale/list/{catelogId}")
    public R getSaleListByCatelogId(@PathVariable("catelogId") Long catelogId) {
        List<AttrEntity> saleAttrList = attrService.getSaleListByCatelogId(catelogId);
        return R.ok().put("data", saleAttrList);
    }

    @GetMapping("/base/listforspu/{spuId}")
    public R getAttrListBySpuId(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> paveList = productAttrValueService.getAttrListBySpuId(spuId);
        return R.ok().put("data", paveList);
    }

    @PutMapping("/update/{spuId}")
    public R updateAttrValBySpuId(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValueEntity> paveList) {
        productAttrValueService.updateSpuAttr(spuId, paveList);
        return R.ok();
    }
}
