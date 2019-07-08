package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtils jwtUtils;
	/**
	 * 发送短信接口
	 * @return
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method= RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){

		userService.sendSms(mobile);
		
		return new Result(true, StatusCode.OK,"发送成功");
	}

	/**
	 * 发送短信接口
	 * @return
	 */
	@RequestMapping(value = "/register/{code}",method= RequestMethod.POST)
	public Result register(@PathVariable String code,@RequestBody User user){


		userService.register(code,user);

		return new Result(true, StatusCode.OK,"注册成功");
	}

	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value = "/login",method= RequestMethod.POST)
	public Result login(@RequestBody User user){

		User loginUser = userService.login(user);

		if(loginUser==null){
			return new Result(false, StatusCode.LOGINERROR,"登录失败");
		}

		//登录成功生成token
		String token = jwtUtils.generateToken(loginUser.getId(), loginUser.getNickname(), "user");

		Map map = new HashMap();
		map.put("token",token);
		map.put("name",loginUser.getNickname());
		map.put("avatar",loginUser.getAvatar());

		return new Result(true, StatusCode.OK,"登录成功",map);
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		Page<User> pageList = userService.findPage(page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<User>(pageList.getTotalElements(), pageList.getContent() ) );
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 登录后,需要与浏览器约定,每次请求服务器都应在请求头中携带token,用于在服务端进行校验
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){

		//1.获得token
		//获得不到=>抛出token异常提示
//		String authorization = request.getHeader("Authorization");
//		if(StringUtils.isEmpty(authorization)){
//			throw  new RuntimeException("请先登录");
//		}
//		//2.token是否是以Bearer 开头
//		if(!authorization.startsWith("Bearer ")){
//			//不是=>抛出异常提示
//			throw new RuntimeException("别黑我");
//		}
//
//		//3.截掉 "Bearer "部分,并解析token
//		String authorizationNew = authorization.substring(7);
//		System.out.println(authorizationNew);
//
//		Claims claims = jwtUtils.parseToken(authorizationNew);
//		//解析失败=>抛出异常提示
//		if(claims==null){
//			throw new RuntimeException("登录过期");
//		}
//
//		//4.判断角色是否为admin
//		String role = (String) claims.get("role");
//		if(!"admin".equals(role)){
//			//不是管理员
//			//如果不是admin,抛出异常提示
//			throw new RuntimeException("没有权限");
//		}

		Object claims_admin = request.getAttribute("claims_admin");

		if(claims_admin==null){
			throw new RuntimeException("没有权限");
		}

		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
