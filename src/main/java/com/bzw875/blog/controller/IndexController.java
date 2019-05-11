package com.bzw875.blog.controller;

import com.bzw875.blog.model.Post;
import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.model.Person;
import com.bzw875.blog.service.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
public class IndexController {
	@Autowired
	private PostRepository postRepository;

	@Value("${system.user.name}")
	private String SystemUserName;

	@Value("${system.user.password}")
	private String systemUserPassword;


	@RequestMapping(value = "/")
	public String index(Model  model) {
		Iterable<Post> posts =  postRepository.findAll();

		for (Post p: posts) {
			String content = p.getContent();
			Integer length = content.length();
			Integer endIndex = Math.min(100, length);
			if (length > 100) {
				content = content.substring(0, endIndex) + "...";
				p.setContent(content);
			}
		}

		model.addAttribute("posts", posts);
		return "index";
	}

	@RequestMapping(value = "/login")
	public String loginPage(Model  model) {
		return "login";
	}

	@RequestMapping(value = "/loginout")
	public void loginOut(HttpServletResponse response, HttpSession session) throws IOException {
		session.setAttribute(WebSecurityConfig.SESSION_KEY, null);
		response.sendRedirect("/");
	}

	@RequestMapping(value = "/login/submit")
	public void login(HttpServletResponse response,
					  RedirectAttributes redirAttrs,
					  HttpSession session,
						@RequestParam String username,
						@RequestParam String password) throws IOException {
		if (SystemUserName.equals(username) && systemUserPassword.equals(password)) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
			redirAttrs.addFlashAttribute("message", "Success");
			System.out.println("登录成功");
			response.sendRedirect("/");
		} else {
			redirAttrs.addFlashAttribute("message", "username or password wrong!");
			System.out.println("登录失败");
			response.sendRedirect("/login");
		}

	}

	@RequestMapping(value = "/write")
	public String write(Model  model) {
		model.addAttribute("author", "bzw875");
		return "write";
	}

	@RequestMapping(value = "/mique")
	public String test(Model  model) {
		Person single = new Person("hyj",21);
		List<Person> people = new ArrayList<Person>();
		Person p1 = new Person("dlp",21);
		Person p2 = new Person("tq",21);
		Person p3 = new Person("mk",21);
		people.add(p1);
		people.add(p2);
		people.add(p3);
		model.addAttribute("singlePerson",single);
		model.addAttribute("people",people);
		return "test";
	}
}