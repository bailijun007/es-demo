package com.leyou.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "blj",type = "item", shards = 1, replicas = 0)
public class Item {
    @Id
    @Field(type = FieldType.Long)
    private Long id;

   // @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @Field(type = FieldType.Text/*,analyzer = "ik_smart"*/) //如果用不到分词器就注释，用到则解开注释
    private String title;//标题

    @Field(type = FieldType.Keyword)
    private String category;//分类

    @Field(type = FieldType.Keyword)
    private String brand;//品牌

    @Field(type = FieldType.Double)
    private Double price;//价格

    @Field(index = false, type = FieldType.Keyword)//图片地址不需要被索引，所以用index = false
    private String images;//图片地址

}
