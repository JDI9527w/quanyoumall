package com.wzy.quanyoumall.product.controller;

import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryControllerTest {
    @Autowired
    private CategoryService CategoryService;
    @Test
    void list() {
        R r = CategoryService.treeSelectCategory();
        System.out.println("r = " + r);
    }

    @Test
    void info() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}