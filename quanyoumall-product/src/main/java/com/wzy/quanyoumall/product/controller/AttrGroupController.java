package com.wzy.quanyoumall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.AttrEntity;
import com.wzy.quanyoumall.product.entity.AttrGroupEntity;
import com.wzy.quanyoumall.product.service.AttrGroupService;
import com.wzy.quanyoumall.product.service.CategoryService;
import com.wzy.quanyoumall.product.vo.AttrGroupVo;
import com.wzy.quanyoumall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
        BeanUtils.copyProperties(attrGroup ,agv);
        agv.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", agv);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
