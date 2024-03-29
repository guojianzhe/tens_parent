package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true, StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		Page<Problem> pageList = problemService.findPage(page, size);
		return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent() ) );
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 增加问题
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){

		//判断当前用户是否登录
		Claims claims_role = (Claims) request.getAttribute("claims_user");
		if(claims_role==null){
			throw new RuntimeException("请先登录");

		}

		problem.setUserid(claims_role.getId());

		problemService.add(problem);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true, StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true, StatusCode.OK,"删除成功");
	}

	/**
	 * 最新问答
	 * /problem/newlist/{label}/{page}/{size}
	 */
	@RequestMapping(value="/newlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result newList(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> problems = problemService.newList(label, page, size);
		return new Result(true, StatusCode.OK,"查询成功",problems);
	}
	/**
	 * 热门问答
	 * /problem/hotlist/{label}/{page}/{size}
	 */
	@RequestMapping(value="/hotlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result hotList(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> problems = problemService.hotList(label, page, size);
		return new Result(true, StatusCode.OK,"查询成功",problems);
	}
	/**
	 * 等待问答
	 * /problem/waitlist/{label}/{page}/{size}
	 */
	@RequestMapping(value="/waitlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result waitList(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> problems = problemService.waitList(label, page, size);
		return new Result(true, StatusCode.OK,"查询成功",problems);
	}


	
}
