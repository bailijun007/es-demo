package com.leyou.es.demo;

import com.leyou.es.pojo.Item;
import com.leyou.es.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreate(){
        //创建索引库
        template.createIndex(Item.class);
        //映射关系
        template.putMapping(Item.class);
    }

    @Test
    public void insertIndex(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void testFind(){
        Iterable<Item> all = itemRepository.findAll();
        for (Item item : all) {
            System.out.println("item  ="+item);
        }
    }

    @Test
    public void findByPriceBetween(){
        List<Item> itemList = itemRepository.findByPriceBetween(2000.00d, 4000.00d);
        for (Item item : itemList) {
            System.out.println("item  ="+item);
        }
    }

    //原生查询
    @Test
    public void testQuery(){
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
        //添加查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机"));
       //排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //分页
        queryBuilder.withPageable(PageRequest.of(0,2));
        //查询
        Page<Item> result = itemRepository.search(queryBuilder.build());
        long total = result.getTotalElements();
        System.out.println("total   ="+total);
        int pages = result.getTotalPages();
        System.out.println("pages  ="+pages);
        for (Item item : result) {
            System.out.println("item    ="+item);
        }


    }

}
