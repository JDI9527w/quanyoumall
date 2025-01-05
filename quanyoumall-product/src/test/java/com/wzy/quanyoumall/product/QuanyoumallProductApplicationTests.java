package com.wzy.quanyoumall.product;

import com.wzy.quanyoumall.product.entity.BrandEntity;
import com.wzy.quanyoumall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuanyoumallProductApplicationTests {


    @Autowired
    private BrandService brandService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("mi");
        boolean save = brandService.save(brandEntity);
        System.out.println("save = " + save);
    }

}
