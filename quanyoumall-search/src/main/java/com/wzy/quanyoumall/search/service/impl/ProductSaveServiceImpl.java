package com.wzy.quanyoumall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.wzy.quanyoumall.search.config.ElasticsearchConfig;
import com.wzy.quanyoumall.search.constant.EsConstant;
import com.wzy.quanyoumall.search.service.ProductSaveService;
import com.wzy.quanyoumall.search.vo.SkuEsVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient esRestClient;

    @Override
    public boolean productUp(List<SkuEsVo> skuEsVoList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsVo SkuEsVo : skuEsVoList) {
            //构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INEDX);
            indexRequest.id(SkuEsVo.getSkuId().toString());
            String jsonString = JSON.toJSONString(SkuEsVo);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = esRestClient.bulk(bulkRequest, ElasticsearchConfig.COMMON_OPTIONS);
        boolean hasFailures = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.info("商品上架完成：{}", collect);
        return hasFailures;
        // 使用java api client无法打印dsl,改用HighLevel
        /*BulkRequestBuilder br = new BulkRequestBuilder();
        for (SkuEsVo skuEsVo : skuEsVoList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(EsConstant.PRODUCT_INEDX)
                            .id(skuEsVo.getSkuId().toString())
                            .document(skuEsVo)
                    )
            );
        }
        BulkResponse result = elasticsearchClient.bulk(br.build());
        boolean flag = true;
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error("itemId:" + item.id() + "error" + item.error().reason());
                }
            }
            flag = false;
        }
        return flag;*/
    }
}
