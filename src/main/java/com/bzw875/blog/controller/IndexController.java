package com.bzw875.blog.controller;

import com.bzw875.blog.model.Post;
import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
public class IndexController {
	@Autowired
	private PostRepository postRepository;

	@RequestMapping(value = "/")
	public String index(Model  model) {
		Iterable<Post> posts =  postRepository.findAll();

		model.addAttribute("posts", posts);
		return "index";
	}

	@RequestMapping(value = "/login")
	public String login(Model  model) {
		return "login";
	}

	@RequestMapping(value = "/write")
	public String write(Model  model) {
		model.addAttribute("author", "bzw875");
		return "write";
	}

	@RequestMapping(value = "/mique")
	public String test(Model  model)
	{
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