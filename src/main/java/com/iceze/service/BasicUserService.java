package com.iceze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iceze.dao.UserDao;
import com.iceze.model.User;

@Service("userService")
public class BasicUserService implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public Optional<List<User>> findAllUsers() {
		List<User> users = this.userDao.findAll();

		return Optional.ofNullable(users);
	}

	@Override
	public Optional<User> findById(final Long id) {
		Optional<User> optionalUser = this.userDao.findById(id);
		
		return optionalUser;
	}

	@Override
	public Optional<User> saveUser(User user) {
		User savedUser = this.userDao.saveAndFlush(user);
		
		return Optional.ofNullable(savedUser);
	}

	@Override
	public void updateUser(User user) {
		this.userDao.saveAndFlush(user);
	}

	@Override
	public void deleteUserById(Long id) {
		this.userDao.delete(id);
	}

	@Override
	public void deleteAllUsers() {
		this.userDao.deleteAll();
	}
}
