package com.champ.gIT.repo;

import org.springframework.data.repository.CrudRepository;
import com.champ.gIT.model.User;

public interface UserRepo extends CrudRepository<User, Long> {

	User findByEmail(String email);

}