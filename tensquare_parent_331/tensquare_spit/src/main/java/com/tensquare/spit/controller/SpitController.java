package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

        return new Result(true, StatusCode.OK,"查询成功",all);
    }


    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);

        return new Result(true, StatusCode.OK,"查询成功",spit);

    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spitService.update(spitId,spit);

        return new Result(true, StatusCode.OK,"修改成功");

    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.delete(spitId);

        return new Result(true, StatusCode.OK,"删除成功");

    }

    ///spit/comment/{parentid}/{page}/{size}
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable Integer page,@PathVariable Integer size){

        PageResult<Spit> pageBean = spitService.findByParentid(parentid, page, size);

        return new Result(true, StatusCode.OK,"查询成功",pageBean);


    }
    @Autowired
    private RedisTemplate redisTemplate;

    //点赞吐槽  /spit/thumbup/{spitId}
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result findByParentid(@PathVariable String spitId){
        //获得当前登录用户的id
        String userid = "666";
        //从redis判断当前登录用户是否已经为该用户点赞
        String user_redis = (String) redisTemplate.boundHashOps("user_redis").get("thumbup_"+userid+"_"+spitId);
        //点过=>返回错误信息
        if("1".equals(user_redis)){
            return new Result(false,StatusCode.REPERROR,"请勿重复点赞");
        }else {

            //未点过=>调用Service点赞,将点赞记录存入redis中
            spitService.thumpup(spitId);

            redisTemplate.boundHashOps("user_redis").put("thumbup_"+userid+"_"+spitId,"1");
            return new Result(true, StatusCode.OK,"点赞成功");
        }







    }



}
