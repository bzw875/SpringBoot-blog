package com.bzw875.blog.model;

import javax.persistence.*;

@Entity
public class Rel_post_tag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post", joinColumns = { @JoinColumn(name ="id" )},
            inverseJoinColumns = { @JoinColumn(name = "role_id") })


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="tag", referencedColumnName = "id")
    private Tag tag;

    public Tag getTag(){
        return tag;
    }
    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
