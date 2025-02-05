package com.wzy.quanyoumall.ware.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.ware.entity.WareOrderTaskEntity;
import com.wzy.quanyoumall.ware.service.WareOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 库存工作单
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@RestController
@RequestMapping("ware/wareordertask")
public class WareOrderTaskController {
    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R queryPageList(WareOrderTaskEntity wareOrderTaskEntity,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        Page<WareOrderTaskEntity> page = new Page<>(pageNum, pageSize);
        Page<WareOrderTaskEntity> result = wareOrderTaskService.queryPage(wareOrderTaskEntity, page);
        return R.ok().put("data", result);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WareOrderTaskEntity wareOrderTask = wareOrderTaskService.getById(id);

        return R.ok().put("wareOrderTask", wareOrderTask);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareOrderTaskEntity wareOrderTask) {
        wareOrderTaskService.save(wareOrderTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody WareOrderTaskEntity wareOrderTask) {
        wareOrderTaskService.updateById(wareOrderTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        wareOrderTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
