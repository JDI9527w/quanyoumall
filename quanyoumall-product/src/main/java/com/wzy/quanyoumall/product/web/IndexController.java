package com.wzy.quanyoumall.product.web;

import com.wzy.quanyoumall.product.entity.CategoryEntity;
import com.wzy.quanyoumall.product.service.CategoryService;
import com.wzy.quanyoumall.product.vo.Catelog2Vo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/product")
public class IndexController {

    @Autowired
    CategoryService categoryService;
    // 1 查询所有的1级分类

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;


    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;

    }

}
