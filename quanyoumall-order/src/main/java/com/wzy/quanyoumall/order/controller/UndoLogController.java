package com.wzy.quanyoumall.order.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.order.entity.UndoLogEntity;
import com.wzy.quanyoumall.order.service.UndoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author wzy
 * @email
 * @date 2025-01-05 21:37:50
 */
@RestController
@RequestMapping("order/undolog")
public class UndoLogController {
    @Autowired
    private UndoLogService undoLogService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        UndoLogEntity undoLog = undoLogService.getById(id);

        return R.ok().put("undoLog", undoLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UndoLogEntity undoLog) {
        undoLogService.save(undoLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UndoLogEntity undoLog) {
        undoLogService.updateById(undoLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        undoLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
