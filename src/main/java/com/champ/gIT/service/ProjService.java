package com.champ.gIT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.champ.gIT.model.Project;
import com.champ.gIT.repo.ProjRepo;

@Component
public class ProjService {
	@Autowired
	ProjRepo repository;
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@ModelAttribute("command")
	public Project searchproj1(String lang, String projname) {
		// String sqlQuery1 = "select * from projects p where p.language=? and
		// p.title=?";

		try {
			List<Project> userDetails = (List<Project>) getHibernateTemplate().find(
					"select language, price,proj_desc,title from projects where language like '%?%' and title like '%?%'");
			System.out.println(userDetails);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return null;

	}

	@SuppressWarnings("rawtypes")
	@ModelAttribute("command")
	public List searchproj(String language, String title) {
		return repository.searchproj(language, title);
	}

}
