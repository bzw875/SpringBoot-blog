package com.bzw875.blog.repository;


import org.springframework.data.repository.CrudRepository;

import com.bzw875.blog.model.Tag;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TagRepository extends CrudRepository<Tag, Long> {

}