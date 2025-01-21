package com.wzy.quanyoumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.quanyoumall.common.utils.PageUtils;
import com.wzy.quanyoumall.common.utils.Query;
import com.wzy.quanyoumall.product.entity.SpuInfoEntity;
import com.wzy.quanyoumall.product.mapper.SpuInfoMapper;
import com.wzy.quanyoumall.product.service.SpuInfoService;
import com.wzy.quanyoumall.product.vo.SpuSaveVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements SpuInfoService {

    @Transactional
    @Override
    public void saveBySpuSaveVo(SpuSaveVo spuSaveVo) {
        /*TODO:保存步骤*/
         /*保存spu信息 pms_spu_info
         保存spu描述图片 pms_spu_info_desc
         保存spu图片集 pms_spu_images
         保存spu规格参数 pms_product_attr_value
         保存spu积分信息 sms_spu_bounds
         保存spu对应的sku
             sku 基本信息 pms_sku_info
             sku 图片信息 pms_sku_images
             sku 销售属性 pms_sku_sale_attr_value
             sku 优惠/满减信息. 优惠 sms_sku_loadder / 满减 sms_sku_full_reduction*/


    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

}