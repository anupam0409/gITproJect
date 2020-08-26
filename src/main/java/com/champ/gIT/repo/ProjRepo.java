package com.champ.gIT.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.champ.gIT.model.Project;

public interface ProjRepo extends CrudRepository<Project, Long> {
	@Query(value = "Select id,language,price,proj_desc,title from Projects where language like %:language% and title like %:title%", nativeQuery = true)
	List<Project> searchproj(String language, String title);
}