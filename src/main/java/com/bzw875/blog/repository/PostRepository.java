package com.bzw875.blog.repository;


import com.bzw875.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete



@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page findByIsDelete(boolean isDelete, Pageable pageable );
}