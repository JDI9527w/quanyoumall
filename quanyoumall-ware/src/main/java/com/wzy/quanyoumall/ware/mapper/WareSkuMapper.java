package com.wzy.quanyoumall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.quanyoumall.common.to.SkuStockTO;
import com.wzy.quanyoumall.ware.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author wzy
 * @email
 * @date 2025-01-05 21:45:59
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    List<SkuStockTO> getStokeBySkuIds(@Param("skuIds") List<Long> skuIds);
}
