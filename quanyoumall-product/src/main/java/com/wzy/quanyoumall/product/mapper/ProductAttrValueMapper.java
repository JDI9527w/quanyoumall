package com.wzy.quanyoumall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.product.entity.ProductAttrValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu属性值
 *
 * @author wzy
 * @email
 * @date 2025-01-05 18:48:48
 */
@Mapper
public interface ProductAttrValueMapper extends BaseMapper<ProductAttrValueEntity> {

    List<ProductAttrValueEntity> listGetNeedSearchAttrBySpuId(@Param("spuId") Long spuId);
}
