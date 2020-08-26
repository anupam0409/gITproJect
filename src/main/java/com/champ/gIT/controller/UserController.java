package com.champ.gIT.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.champ.gIT.exception.UserNotFoundException;
import com.champ.gIT.model.User;
import com.champ.gIT.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService service;

	@RequestMapping(value = { "/getall" }, method = RequestMethod.GET)
	public String getUserList(Model m) {
		m.addAttribute("userList", "Hello world");
		System.out.println("UserList" + service.getUser());
		return "about";
	}

	@RequestMapping(value = { "/signup" })
	public String registrationForm(Model m) {
		User cmd = new User();
		m.addAttribute("command", cmd);
		return "signup";
	}

	@RequestMapping(value = "/register")
	public String registerUser(@ModelAttribute("command") User cmd, Model m) {
		try {
			User u = service.findByEmail(cmd.getEmail());
			if (u != null) {
				m.addAttribute("err", "Email already exists!");
				return "signup";
			}
			String username = cmd.getEmail().substring(0, cmd.getEmail().indexOf('@') - 2)
					.concat(cmd.getPassword().substring(cmd.getPassword().length() - 2));
			cmd.setUsername(username);
			service.add(cmd);
			return "redirect:login?reg=Account+created+with+username+" + username + " !+Please+Login!";
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("err", "Credentials policy mismatched! " + e.getMessage());
			return "signup";
		}
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/loginprocess", method = RequestMethod.POST)
	public String handleLogin(@ModelAttribute("command") User cmd, Model m, HttpSession session) {
		try {
			User loggedInUser = service.findByEmail(cmd.getEmail());
			if (loggedInUser == null) {
				m.addAttribute("err", "Email doesn't exists!");
				return "login";
			} else {
				System.out.println(loggedInUser.getPassword());
				System.out.println(cmd.getPassword());
				if (!loggedInUser.getPassword().equals(cmd.getPassword())) {
					m.addAttribute("err", "Please enter correct password!");
					return "login";
				} else {
					if (loggedInUser.getRole() == UserService.ROLE_ADMIN) {
						addUserInSession(loggedInUser, session);
						System.out.println(loggedInUser.getUsername());
						return "redirect:home";

					} else {
						m.addAttribute("err", "Invalid User Role");
						return "login";
					}
				}
			}
		} catch (UserNotFoundException e) {
			m.addAttribute("err", e.getMessage());
			return "login";
		}
	}

	private void addUserInSession(User u, HttpSession session) {
		session.setAttribute("user", u);
		session.setAttribute("userId", u.getId());
		session.setAttribute("role", u.getRole());
		session.setAttribute("userName", u.getUsername());

	}

	@RequestMapping(value = { "/logout" })
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login?act=You+have+been+logout+successfully!";
	}

	@RequestMapping(value = { "/addproject" })
	public String addproject(Model m, HttpSession session) {
		if (session.getAttribute("userName") == null) {
			m.addAttribute("err", "Login required!!");
			return "login";
		} else {
			return "projadd";
		}
	}

	

	/*
	 * @GetMapping public List<User> getDogs() { return service.getUser(); }
	 * 
	 * @PostMapping public void postDogs(@RequestBody User dto) { service.add(dto);
	 * }
	 * 
	 * @GetMapping("/{id}") public User getById(@PathVariable(required = true) long
	 * id) { return service.getUserById(id); }
	 * 
	 * @DeleteMapping("/{id}") public void delete(@PathVariable(required = true)
	 * long id) { service.delete(id); }
	 */
}
