package com.wzy.quanyoumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.quanyoumall.product.entity.SpuImagesEntity;

import java.util.List;

/**
 * spu图片
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {


    void saveImgBatch(Long id, List<String> images);
}

