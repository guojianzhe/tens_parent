package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    //审核文章
    ///article/examine/{articleId}
    @Modifying  //表示需要修改数据库
    @Query("update Article set state = '1' where id=?1 ")
    public void examine(String articleId);

    //点赞文章
    ///article/thumbup/{articleId}
    @Modifying  //表示需要修改数据库
    @Query("update Article set thumbup = thumbup+1 where id=?1 ")
    public void thumbup(String articleId);
	
}
