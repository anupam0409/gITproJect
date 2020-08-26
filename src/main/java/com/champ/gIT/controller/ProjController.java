package com.champ.gIT.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.champ.gIT.model.Project;
import com.champ.gIT.service.ProjService;

@Controller
public class ProjController {
	@Autowired
	ProjService service;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/searchproject" }, method = RequestMethod.POST)
	public String searchproj(ModelMap m, @ModelAttribute("command") Project cmd) {
		System.out.println(cmd.getLanguage() + cmd.getTitle());
		List pl = service.searchproj(cmd.getLanguage(), cmd.getTitle());
		System.out.println(pl);
		if (pl.size() > 0) {
			m.addAttribute("projList", pl);
			return "projects";
		}
		else {
		m.addAttribute("err", "No Such Project");
		return "index";
		}

	}

}
