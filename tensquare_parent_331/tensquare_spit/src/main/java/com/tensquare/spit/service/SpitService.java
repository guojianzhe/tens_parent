package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private IdWorker idWorker;
    
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    //增加吐槽
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
        spit.setThumbup(0);//点赞
        spit.setVisits(0); //访问量
        spit.setState("1");//状态
        spit.setShare(0); //分享量
        spit.setPublishtime(new Date()); //发布时间
        spit.setComment(0);  //回复量
        if(!StringUtils.isEmpty(spit.getParentid())){
            //该吐槽是其他吐槽的回复,=>其他吐槽的回复加1
            Query query = new Query();

            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");

        }




        spitDao.save(spit);
    }

    //删
    public void delete(String id) {
        spitDao.deleteById(id);
    }

    //改
    public void update(String id, Spit spit) {
        spit.set_id(id);
        spitDao.save(spit);
    }

    //查 根据id
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    //查 查询所有
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    ///spit/comment/{parentid}/{page}/{size}
    public PageResult<Spit> findByParentid(String parentId,Integer page,Integer size){


        Page pagesize = spitDao.findByParentid(parentId, PageRequest.of(page-1, size));

        return  new PageResult<Spit>(pagesize.getTotalElements(),pagesize.getContent());
    }


    //点赞吐槽

    public void thumpup(String spitId){
        Query query = new Query();

        query.addCriteria(Criteria.where("_id").is(spitId));

        Update update = new Update();
        update.inc("thumbup",1);

        mongoTemplate.updateFirst(query,update,"spit");
    }



}
