package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.product.entity.SpuImagesEntity;
import com.wzy.quanyoumall.product.mapper.SpuImagesMapper;
import com.wzy.quanyoumall.product.service.SpuImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImagesEntity> implements SpuImagesService {

    @Override
    public void saveImgBatch(Long id, List<String> images) {
        if (images != null && images.size() > 0) {
            List<SpuImagesEntity> imgList = images.stream().map(img -> {
                SpuImagesEntity spuImg = new SpuImagesEntity();
                spuImg.setSpuId(id);
                spuImg.setImgUrl(img);
                return spuImg;
            }).collect(Collectors.toList());
            this.saveBatch(imgList);
        }
    }

}