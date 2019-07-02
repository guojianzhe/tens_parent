package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.LabelView;
import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin  //允许跨域访问
public class LabelController {


    @Autowired
    private LabelService labelService;


    //增
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {

        labelService.save(label);
        return new Result(StatusCode.OK,true,"添加成功");
    }

    //删
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String labelId) {

        labelService.delete(labelId);
        return new Result(StatusCode.OK,true,"删除成功");
    }

    //改
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label) {
        labelService.update(labelId,label);
        return new Result(StatusCode.OK,true,"修改成功");
    }

    //查 根据id
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId) {
//        int i = 1/0;
        Label label = labelService.findById(labelId);
        return new Result(StatusCode.OK,true,"查询成功",label);

    }

    //查 查询所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Label> labelList = labelService.findAll();
        return new Result(StatusCode.OK,true,"查询成功",labelList);

    }

    //查 条件查询
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findByCondition(@RequestBody Label label) {
        List<Label> labelList = labelService.findByCondition(label);
        return new Result(StatusCode.OK,true,"查询成功",labelList);
    }

    //查 分页条件查询
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findPageByCondition(@PathVariable Integer page,@PathVariable Integer size,@RequestBody Label label) {
        PageResult<Label> pageResult = labelService.findPageByCondition(page, size, label);
        return new Result(StatusCode.OK,true,"查询成功",pageResult);
    }


}
