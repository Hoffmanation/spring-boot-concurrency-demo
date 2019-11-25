package com.watertank.test.dao;

import java.util.Map;
import java.util.Set;

import com.watertank.test.entity.User;

/**
 * DAO layer class for UserService entity
 * 
 * @author The Hoff
 *
 */
public interface UserService {

	public User createOrSaveUser(User user);

	public User findById(Integer id);

	public User findByEmail(String email);

	public User findByUserPasswordAndEmail(String passwors, String email);

	public User findByUsername(String username);

	public Map<Integer,User> findAll();

}
