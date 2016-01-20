package com.ai.wocloud.dao.interfaces;

import java.util.List;

import com.ai.wocloud.bean.User;

/**
 * 
 */
/**
 * ClassName: UserDao <br/>
 * Function: 用户信息 <br/>
 * date: 2014-12-19 11:13:21 <br/>
 *
 * @version 
 * @since JDK 1.6
 */
public interface UserDao {

	/**
	 * insertUser: <br/>
	 * 插入一条用户信息<br/>
	 *
	 * @param user
	 * @since JDK 1.6
	 */
	public abstract void insertUser(User user);

	/**
	 * updateUser: <br/>
	 * 更新用户信息<br/>
	 *
	 * @param user
	 * @since JDK 1.6
	 */
	public abstract void updateUser(User user);

	/**
	 * deleteUser: <br/>
	 * 删除一条用户信息<br/>
	 *
	 * @param userId
	 * @since JDK 1.6
	 */
	public abstract void deleteUser(Integer userId);

	/**
	 * findUserByid: <br/>
	 * 根据ID查找用户信息<br/>
	 *
	 * @param userId
	 * @return
	 * @since JDK 1.6
	 */
	public abstract User findUserByid(Integer userId);

	/**
	 * findAll: <br/>
	 * 查询所有用户信息<br/>
	 *
	 * @return
	 * @since JDK 1.6
	 */
	public abstract List<User> findAll();

	/**
	 * userLogin: <br/>
	 * 用户登录<br/>
	 *
	 * @param user
	 * @return
	 * @since JDK 1.6
	 */
	public abstract User userLogin(User user);

}