package com.wzy.quanyoumall.product.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.entity.CommentReplayEntity;
import com.wzy.quanyoumall.product.service.CommentReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 商品评价回复关系
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@RestController
@RequestMapping("product/commentreplay")
public class CommentReplayController {
    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * 列表
     */


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CommentReplayEntity commentReplay = commentReplayService.getById(id);

        return R.ok().put("commentReplay", commentReplay);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.save(commentReplay);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CommentReplayEntity commentReplay) {
        commentReplayService.updateById(commentReplay);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        commentReplayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
