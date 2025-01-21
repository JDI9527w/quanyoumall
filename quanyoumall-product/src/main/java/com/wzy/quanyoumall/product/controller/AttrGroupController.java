package com.wzy.quanyoumall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.constant.ProductConstant;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.AttrAttrgroupRelationEntity;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import com.wzy.quanyoumall.product.service.AttrAttrgroupRelationService;
import com.wzy.quanyoumall.product.service.AttrGroupService;
import com.wzy.quanyoumall.product.service.AttrService;
import com.wzy.quanyoumall.product.service.CategoryService;
import com.wzy.quanyoumall.product.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * 属性分组
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(AttrGroupEntity attrGroup,
                  @RequestParam Integer pageNum,
                  @RequestParam Integer pageSize) {
        Page<AttrGroupEntity> page = new Page<>(pageNum, pageSize);
        Page<AttrGroupEntity> attrGroupEntityPage = attrGroupService.queryPage(page, attrGroup);
        return R.ok().put("data", attrGroupEntityPage);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        List<Long> catelogPath = categoryService.queryLevelCidsBycatelogId(attrGroup.getCatelogId());
        Collections.reverse(catelogPath);
        AttrGroupVo agv = new AttrGroupVo();
        BeanUtils.copyProperties(attrGroup, agv);
        agv.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", agv);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> attrGroupIds) {
        Set<Long> groupIds = attrGroupService.removeByGroupIds(attrGroupIds);
        if (groupIds.size() > 0) {
            return R.error("id:" + groupIds.toString() + "已关联属性,请移除后再试.");
        }
        return R.ok();
    }

    @GetMapping("{attrGroupId}/attr/relation")
    public R getRelationByAttrGroupId(@PathVariable(value = "attrGroupId") String attrGroupId) {
        List<AttrEntity> attrs = attrGroupService.getRelationByAttrGroupId(attrGroupId);
        return R.ok().put("data", attrs);
    }

    @DeleteMapping("/attr/relation/delete")
    public R relationDelete(@RequestBody List<AttrAttrgroupRelationEntity> relationEntities) {
        attrAttrgroupRelationService.deleteByAttrIdAndGroupId(relationEntities);
        return R.ok();
    }

    @GetMapping("/{attrGroupId}/noattr/relation")
    public R checkAllnoAttrByGroupId(@PathVariable("attrGroupId") String attrGroupId,
                                     @RequestParam(required = false) String paramName,
                                     @RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {
        Page<AttrEntity> page = new Page<>(pageNum, pageSize);
        Page<AttrEntity> attrEntities = attrGroupService.selectAllNoAttrByGroupId(page, attrGroupId, paramName, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        return R.ok().put("data", attrEntities);
    }

    @PostMapping("attr/relation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> list) {
        attrAttrgroupRelationService.saveBatch(list);
        return R.ok();
    }

    @GetMapping("/{catId}/withattr")
    public R getAttrListByCatId(@PathVariable("catId") Long catId) {
        List<AttrGroupVo> attrGroupVos = attrGroupService.getAttrListByCatId(catId);
        return R.ok().put("data", attrGroupVos);
    }

}
