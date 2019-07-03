package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class SpitService {
    @Autowired
    private IdWorker idWorker;
    
    @Autowired
    private SpitDao spitDao;

    //增加吐槽
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
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



}