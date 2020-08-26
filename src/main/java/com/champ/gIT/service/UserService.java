package com.champ.gIT.service;

import com.champ.gIT.exception.UserNotFoundException;
import com.champ.gIT.model.User;
import com.champ.gIT.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
	public static final Integer ROLE_ADMIN = 1;
	public static final Integer ROLE_USER = 0;
	@Autowired
	UserRepo repository;

	@ModelAttribute("command")
	public void add(User dto) {
		repository.save(toEntity(dto));
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	@ModelAttribute("userList")
	public List<User> getUser() {
		return (List<User>) repository.findAll();
	}

	public User getUserById(long id) {
		Optional<User> optionalUser = repository.findById(id);
		return optionalUser.orElseThrow(() -> new UserNotFoundException("Couldn't find a User with id: " + id));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	private User toEntity(User dto) {
		User entity = new User();
		entity.setUsername(dto.getUsername());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		return entity;
	}

}