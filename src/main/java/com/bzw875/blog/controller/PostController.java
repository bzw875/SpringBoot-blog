package com.bzw875.blog.controller;


import com.bzw875.blog.model.Post;
import com.bzw875.blog.model.Tag;
import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.repository.TagRepository;
import com.bzw875.blog.service.WebSecurityConfig;
import com.bzw875.blog.utils.MarkDown2HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

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

    @PostMapping(path="/delete/{id}")
    public void delete(HttpServletResponse response,
                       @PathVariable Integer id
    ) throws IOException {
        Post pp = postRepository.findById(id).get();
        pp.setIsDelete(true);
        postRepository.save(pp);
        response.sendRedirect("/");
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path="/detail/{id}")
    public String getPostDetail (Model model,
                                 HttpServletRequest request,
                                 @PathVariable Integer id) {
        Post pp = postRepository.findById(id).get();
        String postContentHtml = MarkDown2HtmlUtils.markdown2Html(pp.getContent());
        model.addAttribute("post", pp);
        model.addAttribute("postContentHtml", postContentHtml);
        Cookie[] cookies =  request.getCookies();
        boolean isLogin = false;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(WebSecurityConfig.SESSION_KEY)){
                    isLogin = true;
                }
            }
        }
        model.addAttribute("isLogin", isLogin);
        if (!isLogin) {
            pp.setVisits(pp.getVisits() + 1);
            postRepository.save(pp);
        }

        return "post";
    }


    @PostMapping(path="/edit/")
    public void editPost (HttpServletResponse response,
                            @RequestParam Integer id,
                            @RequestParam String content,
                          @RequestParam(value = "tags", required = false) List<String> tags,
                            @RequestParam String title) throws IOException {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();
        System.out.println(tags);

        Post n = new Post();
        n.setId(pp.getId());
        n.setTitle(title);
        n.setContent(content);
        n.setIsDelete(false);
        n.setAuthor(pp.getAuthor());
//        if (tags == null) {
//            n.setTags("");
//        } else {
//            n.setTags(StringUtils.join(tags, ","));
//        }
        n.setCreationTime(pp.getCreationTime());
        n.setModificationTime( new Date() );
        postRepository.save(n);
        response.sendRedirect("/post/detail/" + pp.getId());
    }

//    @GetMapping(path="/tag/{id}")
//    public String searchByTag (Model model, @PathVariable Integer id) {
//    }


    @GetMapping(path="/edit/{id}")
    public String editPost (Model model, @PathVariable Integer id) {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();
        Iterable<Tag> tags = tagRepository.findAll();
        model.addAttribute("post", pp);
        model.addAttribute("tags", tags);
        model.addAttribute("author", "bzw875");
        return "edit";
    }
}