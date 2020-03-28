package com.bzw875.blog.controller;


import com.bzw875.blog.model.Post;
import com.bzw875.blog.model.PostTag;
import com.bzw875.blog.model.Tag;
import com.bzw875.blog.repository.PostRepository;
import com.bzw875.blog.repository.PostTagRepository;
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
import java.util.*;

@Controller
@RequestMapping(path="/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping(path="/add")
    public String addNewPost (@RequestParam String author,
                              @RequestParam String content,
                              @RequestParam(value = "tags", required = false) List<Integer> tags,
                              @RequestParam String title) {

        Date d = new Date();

        Post n = new Post();
        n.setContent(content);
        n.setTitle(title);
        n.setVisits(0);
        n.setIsDelete(false);
        n.setAuthor(author);
        n.setCreationTime(d);
        n.setModificationTime(d);
        postRepository.save(n);

        if (tags!=null) {
            for (int tagid : tags) {
                PostTag pt = new PostTag();
                pt.setPost(n);
                pt.setTag(tagRepository.findById(tagid).get());
                postTagRepository.save(pt);
            }
        }
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

    @GetMapping(path="/tag/{id}")
    public String getPostByTag(Model model,
                               @PathVariable Integer id,
                               HttpServletRequest request) {
        List<PostTag> pts = postTagRepository.findPostTagsByTagId(id);
        List<Post> pages = new ArrayList<>();
        for (PostTag tmp : pts) {
            pages.add(tmp.getPost());
        }
        Integer total = pages.size();
        model.addAttribute("posts", pages);
        model.addAttribute("pageNum", 1);
        model.addAttribute("pageSize", 10);
        model.addAttribute("pageCount", 1);
        model.addAttribute("total", total);
        model.addAttribute("sort", "asc");
        model.addAttribute("posts", pages);
        return "index";
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
        List<PostTag> pts = postTagRepository.findPostTagsByPostId(id);
        List<Tag> tags = new ArrayList<Tag>();
        for (PostTag a : pts) {
            tags.add(a.getTag());
        }
        model.addAttribute("tags", tags);

        return "post";
    }


    @PostMapping(path="/edit/")
    public void editPost (HttpServletResponse response,
                            @RequestParam Integer id,
                            @RequestParam String content,
                          @RequestParam(value = "tags", required = false) List<Integer> tags,
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
        n.setCreationTime(pp.getCreationTime());
        n.setModificationTime( new Date() );
        postRepository.save(n);

        List<PostTag> pts = postTagRepository.findPostTagsByPostId(id);
        for (PostTag pt : pts) {
            if (tags.indexOf(pt.getTag().getId()) == -1) {
                postTagRepository.delete(pt);
            }
        }
        for (Integer tagid : tags) {
            boolean isFind = false;
            for (PostTag pt : pts) {
                if (tagid == pt.getTag().getId()) {
                    isFind = true;
                }
            }
            if (!isFind) {
                PostTag ptnew = new PostTag();
                Optional<Tag> optag = tagRepository.findById(tagid);

                ptnew.setTag(optag.get());
                ptnew.setPost(n);
                postTagRepository.save(ptnew);
            }
        }
        response.sendRedirect("/post/detail/" + pp.getId());
    }



    @GetMapping(path="/edit/{id}")
    public String editPost (Model model, @PathVariable Integer id) {
        Optional<Post> p = postRepository.findById(id);
        Post pp = p.get();
        Iterable<Tag> tags = tagRepository.findAll();
        List<PostTag> pts = postTagRepository.findPostTagsByPostId(id);
        String postTagIds = "";
        for (PostTag a : pts) {
            postTagIds += a.getTag().getId() + ",";
        }
        model.addAttribute("tags", tags);
        model.addAttribute("post", pp);
        model.addAttribute("postTagIds", postTagIds);
        model.addAttribute("author", "bzw875");
        return "edit";
    }
}