package com.wzy.quanyoumall.search.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.wzy.quanyoumall.common.utils.R;
import com.wzy.quanyoumall.search.vo.SkuEsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/es/save")
public class EsSaveController {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @PostMapping("/product")
    public R productUp(@RequestBody List<SkuEsVo> skuEsVoList) throws IOException {
        // TODO:保存是否正确,id?
        IndexResponse productRes = elasticsearchClient.index(i -> i.index("product").document(skuEsVoList));
        // 解析保存结果
        return R.ok();
    }

}
