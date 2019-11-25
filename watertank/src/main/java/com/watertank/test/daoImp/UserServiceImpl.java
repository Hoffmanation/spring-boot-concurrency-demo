package com.watertank.test.daoImp;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.watertank.test.dao.UserService;
import com.watertank.test.entity.User;

/**
 * A Service Impl class For the User Entity
 * 
 * @author The Hoff
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private ConcurrentHashMap<Integer, User> users;

	public UserServiceImpl() {
		users = new ConcurrentHashMap<>();
	}

	@Override
	public User createOrSaveUser(User user) {
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		for (Entry<Integer, User> entry : users.entrySet()) {
			User tempUser = entry.getValue();
			if (tempUser.getEmail().equals(email)) {
				user = tempUser;
			}
		}

		return user;
	}

	@Override
	public User findById(Integer id) {
		return users.get(id);
	}

	@Override
	public User findByUserPasswordAndEmail(String password, String email) {
		User user = null;
		for (Entry<Integer, User> entry : users.entrySet()) {
			User tempUser = entry.getValue();
			if (tempUser.getPassword().equals(password) && tempUser.getEmail().equals(email)) {
				user = tempUser;
			}
		}

		return user;
	}

	@Override
	public User findByUsername(String username) {
		User user = null;
		for (Entry<Integer, User> entry : users.entrySet()) {
			User tempUser = entry.getValue();
			if (tempUser.getUsername().equals(username)) {
				user = tempUser;
			}
		}

		return user;
	}

	@Override
	public Map<Integer, User> findAll() {
		return users;
	}

}