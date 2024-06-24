package com.example.emoney.repositories;

import com.example.emoney.models.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    List<Comment> findCommentsByBlogId(ObjectId blogId);

    Page<Comment> findCommentsByBlogId(ObjectId blogId, Pageable pageable);


    long countByBlogId(ObjectId blogId);
}

