package com.wzy.quanyoumall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.product.entity.SkuSaleAttrValueEntity;
import com.wzy.quanyoumall.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> listGetsaleAttrsBySpuId(@Param("spuId") Long spuId);

    List<String> getSaleAttrListBySkuId(@Param("skuId") Long skuId);
}
