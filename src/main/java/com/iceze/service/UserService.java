package com.iceze.service;

import java.util.List;
import java.util.Optional;

import com.iceze.model.User;

public interface UserService {
	Optional<List<User>> findAllUsers();
	Optional<User> findById(final Long id);
	Optional<User> saveUser(final User user);
	void updateUser(final User user);
	void deleteUserById(final Long id);
	void deleteAllUsers();
}
