package com.tensquare.search.pojo;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "tensquare",type = "article")
public class Article {

    @Id
    private String id;

    //store  是否存储
    //index 是否索引
    @Field(store = true,index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(store = true,index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;
    @Field(store = true)
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
