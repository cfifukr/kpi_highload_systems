package com.example.emoney.services;

import com.example.emoney.models.Comment;
import com.example.emoney.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment saveOrUpdateComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(ObjectId id){
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(ObjectId id){
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBlog(ObjectId id){
       return commentRepository.findCommentsByBlogId(id);
    }

    @Transactional
    public Long totalOfCommentsToBlog(ObjectId id){
        return commentRepository.countByBlogId(id);
    }

    @Transactional(readOnly = true)
    public Page<Comment> getPageCommentsByBlog(ObjectId id, Pageable pageable){
        return commentRepository.findCommentsByBlogId(id, pageable);
    }
}
