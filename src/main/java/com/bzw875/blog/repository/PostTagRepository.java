package com.bzw875.blog.repository;


import com.bzw875.blog.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete



@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    List<PostTag> findPostTagsByPostId(Integer id);
    List<PostTag> findPostTagsByTagId(Integer id);
}