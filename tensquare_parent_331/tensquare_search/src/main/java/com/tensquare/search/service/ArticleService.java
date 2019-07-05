package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    //保存文章
    public void save(Article article){
        articleDao.save(article);
    }
    //根据标题内容搜索文章
    public PageResult<Article> findByTitle(String keyword, Integer page, Integer size){


       Page<Article> pageBean = articleDao.findByTitleLikeOrContentLike(keyword,keyword, PageRequest.of(page-1,size));

       return new PageResult<>(pageBean.getTotalElements(),pageBean.getContent());

    }

}
