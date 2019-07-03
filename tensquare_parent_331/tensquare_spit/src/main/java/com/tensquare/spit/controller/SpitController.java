package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);

        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> all = spitService.findAll();

        return new Result(true, StatusCode.OK,"添加成功",all);
    }


    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);

        return new Result(true, StatusCode.OK,"添加成功",spit);

    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spitService.update(spitId,spit);

        return new Result(true, StatusCode.OK,"修改成功");

    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result update(@PathVariable String spitId){
        spitService.delete(spitId);

        return new Result(true, StatusCode.OK,"删除成功");

    }

}
