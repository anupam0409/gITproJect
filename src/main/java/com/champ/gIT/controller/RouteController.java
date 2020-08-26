package com.champ.gIT.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
	@RequestMapping(value = { "/home", "/"})
	public String home() {
		return "index";
	}

	@RequestMapping("/blog")
	public String blog() {
		return "blog";
	}

	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}

	@RequestMapping("/features")
	public String features() {
		return "features";
	}

	@RequestMapping("/pricing")
	public String pricing() {
		return "pricing";
	}

}
