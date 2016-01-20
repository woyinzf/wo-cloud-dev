package com.ai.wocloud.service.interfaces;

import java.util.List;

import com.ai.wocloud.bean.User;

/**
 * 
 */
public interface UserService {

	public abstract void addUser(User user);

	public abstract void updateUser(User user);

	public abstract void deleteUser(Integer userId);

	public abstract User findUserById(Integer userId);

	public abstract List<User> findAllUser();

	public abstract User login(User user);

}