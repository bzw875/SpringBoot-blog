package com.bzw875.blog.controller;

import com.bzw875.blog.model.Person;
import com.bzw875.blog.model.Post;
import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.service.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
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

	@Value("${spring.security.user.name}")
	private String SystemUserName;

	@Value("${spring.security.user.password}")
	private String systemUserPassword;

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value = "/")
	public String index(Model  model,
		@RequestParam(value = "pageNum", required = true, defaultValue = "0") Integer pageNum,
		@RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
		PageRequest pageReques = PageRequest.of(pageNum, pageSize);   //获取第1页的两条记录
		Page<Post> page = postRepository.findAll(pageReques);
		List<Post> pages= page.getContent();
		int total = page.getTotalPages() * pageSize;
		int pageCount = (int) Math.ceil(total/pageSize);

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("total", total);
		model.addAttribute("posts", pages);

		return "index";
	}

	@RequestMapping(value = "/login")
	public String loginPage(Model  model) {
		return "login";
	}

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(HttpServletResponse response,
						  @RequestParam String username,
						  @RequestParam String password,
						  Model model){

		if(username.equals(SystemUserName) && password.equals(systemUserPassword)){
			Cookie cookie = new Cookie(WebSecurityConfig.SESSION_KEY, SystemUserName);
			response.addCookie(cookie);
			return "redirect:/";
		}else {
			model.addAttribute("error", "用户名或者密码错误");

			return "admin/login";
		}
	}

	@RequestMapping(value = "/loginout")
	public void loginOut(HttpServletResponse response, HttpSession session) throws IOException {
		Cookie cookie = new Cookie(WebSecurityConfig.SESSION_KEY, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		response.sendRedirect("/");
	}



	@RequestMapping(value = "/write")
	public String write(Model  model) {
		model.addAttribute("author", "bzw875");
		return "write";
	}


	@RequestMapping(value = "/mique")
	public String mique(Model  model) {
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
		return "admin";
	}
}