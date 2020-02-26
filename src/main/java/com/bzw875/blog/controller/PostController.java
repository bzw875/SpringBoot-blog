package com.bzw875.blog.controller;


import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path="/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping(path="/add")
    public String addNewPost (@RequestParam String author
            , @RequestParam String content
            , @RequestParam String title) {

        Date d = new Date();

        Post n = new Post();
        n.setContent(content);
        n.setTitle(title);
        n.setAuthor(author);
        n.setCreationTime(d);
        n.setModificationTime(d);
        postRepository.save(n);
        return "write";
    }

    @GetMapping(path="/delete/{id}")
    public void delete(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        postRepository.deleteById(id);
        response.sendRedirect("/");
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path="/detail/{id}")
    public String getPostDetail (Model model, @PathVariable Integer id) {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();
        model.addAttribute("post", pp);
        return "post";
    }


    @PostMapping(path="/edit/{id}")
    public void editPost (HttpServletResponse response,
                            @RequestParam Integer id,
                            @RequestParam String content,
                            @RequestParam String title) throws IOException {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();

        Post n = new Post();
        n.setId(pp.getId());
        n.setTitle(title);
        n.setContent(content);
        n.setAuthor(pp.getAuthor());
        n.setCreationTime(pp.getCreationTime());
        n.setModificationTime( new Date() );
        postRepository.save(n);
        response.sendRedirect("/post/detail/" + pp.getId());
    }


    @GetMapping(path="/edit/{id}")
    public String editPost (Model model, @PathVariable Integer id) {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();
        model.addAttribute("post", pp);
        System.out.printf("编辑页面");
        return "edit";
    }
}