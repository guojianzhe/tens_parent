package com.tensquare.qa.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    //需求分析：最新回复的问题显示在上方， 按回复时间降序排序。
    //nativeQuery = true  是否使用sql语句
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?1) ORDER BY replytime desc",nativeQuery = true)
    public List<Problem> NewList(String labelid, PageRequest pageRequest);
    //热门问答:需求分析：按回复数降序排序
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?1) ORDER BY reply desc",nativeQuery = true)
    public List<Problem> hotList(String labelid, PageRequest pageRequest);

    //等待回答
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?1) and reply = 0 ORDER BY replytime desc",nativeQuery = true)
    public List<Problem> waitList(String labelid, PageRequest pageRequest);
}
