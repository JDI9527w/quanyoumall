package com.wzy.quanyoumall.product.vo;

import com.wzy.quanyoumall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupVo {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 分类树路径
     */
    private List<Long> catelogPath;
    /**
     * 组内属性
     */
    private List<AttrEntity> attrList;
}
