package com.iceze.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.iceze.model.User;

public interface UserDao extends JpaRepository<User, Long> {
	List<User> findAll();
	Optional<User> findById(Long id);
	Optional<User> retrieveByName(@Param("name") final String name);
	User saveAndFlush(User user);
	void delete(Long id);
	void deleteAll();
}
