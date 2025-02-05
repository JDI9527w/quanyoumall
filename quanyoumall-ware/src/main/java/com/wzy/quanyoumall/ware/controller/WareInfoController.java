package com.wzy.quanyoumall.ware.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.WareInfoEntity;
import com.wzy.quanyoumall.ware.service.WareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 仓库信息
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R queryPageList(WareInfoEntity wareInfoEntity,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        Page<WareInfoEntity> page = new Page<>(pageNum, pageSize);
        Page<WareInfoEntity> result = wareInfoService.queryPage(wareInfoEntity, page);

        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WareInfoEntity wareInfo = wareInfoService.getById(id);

        return R.ok().put("wareInfo", wareInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.save(wareInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.updateById(wareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
