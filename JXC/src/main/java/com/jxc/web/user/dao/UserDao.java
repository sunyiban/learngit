package com.jxc.web.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.UserEntity;

public abstract interface UserDao {
	//登陆
	public  abstract List<UserEntity> queryUserforLogin(HashMap map);
	//新增用户
	public 	abstract void saveUserforRegist(UserEntity users);
	//查询用户名是否存在
	public int queryIsUserNameExist(String username);
	//更新用户柜台关系表
	public void insertUserCounter(UserEntity users);
	//查询
	public abstract List<UserEntity> queryUser();
	//保存用户身份
	public abstract List<UserEntity> saveUserInfo(String username);
	//根据用户ID查询柜台名，柜台号，柜台所属地
	public abstract List<CounterEntity> queryCountry(String id);
	//更新用户密码
	public abstract void updateUser(String username, String newpassword);
	//查询优惠天数
	public List<Map> discountday();
	//修改优惠天数
	public void changediscountday(Map map);
}