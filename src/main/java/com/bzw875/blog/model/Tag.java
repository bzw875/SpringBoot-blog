package com.bzw875.blog.model;

import javax.persistence.*;
import java.util.Set;


@Entity // This tells Hibernate to make a table out of this class
public class Tag {

    private Integer id;

    private String name;

    private Set<PostTag> postTags;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "post")
    public Set<PostTag> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<PostTag> postTags) {
        this.postTags = postTags;
    }
}
