package com.wzy.quanyoumall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.wzy.quanyoumall.search.constant.EsConstant;
import com.wzy.quanyoumall.search.service.MallSearchService;
import com.wzy.quanyoumall.search.utils.ConvertToFieldValue;
import com.wzy.quanyoumall.search.vo.SearchParam;
import com.wzy.quanyoumall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public SearchResult search(SearchParam param) {
        SearchRequest searchRequest = this.buildSearchRequest(param);
        SearchResult searchResult = null;
        try {
            SearchResponse<SearchResult> searchResponse = esClient.search(searchRequest, SearchResult.class);
            searchResult = this.buildSearchResult(searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public SearchRequest buildSearchRequest(SearchParam param) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
/*     SearchRequest searchRequest = SearchRequest.of(s -> s
            .index("your_index_name")          // 索引名称
            .query(nestedQuery)                // 设置查询条件
            .sort(sortByTime)                  // 设置排序规则
            .size(10)                          // 返回结果数量（可选）
        );
        .terms(t -> t.field("c").terms(TermsQueryField.of(ts -> ts.value(cValues)))*/
        //构建DSL语句
        //1.1 bool-must模糊匹配
        if (StringUtils.isNotEmpty(param.getKeyword())) {
            boolQueryBuilder.must(q -> q.match(m -> m.field("skuTitle").query(param.getKeyword())));
        }
        //1.2.1 bool-filter -按照三级分类ID查询
        if (param.getCatalog3Id() != null) {
            boolQueryBuilder.filter(q -> q.term(t -> t.field("catalogId").value(param.getCatalog3Id())));
        }
        //1.2.2 按照品牌ID查询
        List<Long> brandIds = param.getBrandId();
        List<FieldValue> fieldValueList = ConvertToFieldValue.convertWithList(brandIds);
        if (fieldValueList.size() > 0) {
            boolQueryBuilder.filter(q -> q.terms(t -> t.field("brandId").terms(ts -> ts.value(fieldValueList))));
//            q -> q.terms(t -> t.field("brandId").terms(ts -> ts.value(fieldValueList)))
//            q -> q.terms(t -> t.field("brandId").terms(TermsQueryField.of(ts -> ts.value(fieldValueList))))
        }
        //1.2.3 按照所有指定的属性进行查询
        if (param.getAttrs().size() > 0) {
            for (String attr : param.getAttrs()) {
                NestedQuery.Builder nestedQueryBuilder = new NestedQuery.Builder();
                String[] attrArr = attr.split("_");
                String attrId = attrArr[0];
                String[] values = attrArr[1].split(":");
                nestedQueryBuilder.path("attrs").scoreMode(ChildScoreMode.None);
                if (values.length == 1) {
                    nestedQueryBuilder.query(q -> q.bool(b -> b
                            .must(m -> m.term(t -> t.field("attrs.attrId").value(attrId)))
                            .must(m -> m.term(t -> t.field("attrs.attrValue").value(values[0])))));
                } else {
                    List<FieldValue> termsVal = ConvertToFieldValue.convertWithList(Arrays.asList(values));
                    nestedQueryBuilder.query(q -> q.bool(b -> b
                            .must(m -> m.term(t -> t.field("attrs.attrId").value(attrId)))
                            .must(m -> m.terms(t -> t.field("attrs.attrValue").terms(ts -> ts.value(termsVal))))));
                }
                boolQueryBuilder.filter(nestedQueryBuilder.build()._toQuery());
            }
        }
        //1.2.4 按照是否有库存查询
        if (param.getHasStock() != null) {
            boolQueryBuilder.filter(q -> q.term(t -> t.field("hasStock").value(param.getHasStock())));
        }
        //1.2.5 按照价格区间查询
        if (StringUtils.isNotEmpty(param.getSkuPrice())) {
            String[] priceArr = param.getSkuPrice().split("_");
            if (StringUtils.isNotBlank(priceArr[0])) {
                JsonData min = JsonData.of(priceArr[0]);
                boolQueryBuilder.filter(q -> q.range(r -> r.field("price").gte(min)));
            }
            if (StringUtils.isNotBlank(priceArr[1])) {
                JsonData max = JsonData.of(priceArr[1]);
                boolQueryBuilder.filter(q -> q.range(r -> r.field("price").lte(max)));
            }
        }

        //2.1 排序
        List<SortOptions> sortOptionsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(param.getSort())) {
            String[] sortArr = param.getSort().split("_");
            SortOptions sortOptions = SortOptions.of(so -> so.field(f -> f.field(sortArr[0]).order(sortArr[1].equals(SortOrder.Asc.jsonValue()) ? SortOrder.Asc : SortOrder.Desc)));
            sortOptionsList.add(sortOptions);
        }
        // 5. 构建完整查询
        // 6. 创建搜索请求
        SearchRequest searchRequest = SearchRequest.of(s -> {
            s.index("product")          // 索引名称
                    .query(boolQueryBuilder.build()._toQuery())// 设置查询条件
                    .sort(sortOptionsList.isEmpty() ? null : sortOptionsList)
                    .size(10)                          // 返回结果数量（可选）
                    .from((param.getPageNum() - 1) * EsConstant.PAGE_SIZE)       //2.2 分页
                    .size(EsConstant.PAGE_SIZE);
            // 高亮
            if (StringUtils.isNotEmpty(param.getKeyword())) {
                s.highlight(hi -> hi.fields("skuTitle", hlb -> hlb.preTags("<b style='color:red'>").postTags("</b>")));
            }
            return s;
        });
        return searchRequest;
    }

    public SearchResult buildSearchResult(SearchResponse<SearchResult> searchResponse) {
        return null;
    }

}