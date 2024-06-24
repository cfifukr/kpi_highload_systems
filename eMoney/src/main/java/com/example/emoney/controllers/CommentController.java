package com.example.emoney.controllers;


import com.example.emoney.dtos.pageable.CommentPageDto;
import com.example.emoney.dtos.request.CommentDto;
import com.example.emoney.dtos.response.CommentResponseDto;
import com.example.emoney.models.Blog;
import com.example.emoney.models.Comment;
import com.example.emoney.services.BlogService;
import com.example.emoney.services.CommentService;
import com.example.emoney.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs/{blog_id}/comments")
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {
    private final BlogService blogService;
    private final CommentService commentService;
    private final JwtService jwtService;


    @GetMapping
    public ResponseEntity<?> getCommentsToBlog(@PathVariable ObjectId blog_id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        //get page 0f comments comments - > comments to commentsDto -> page to list
        List<CommentResponseDto> comments = commentService.getPageCommentsByBlog(blog_id, pageable)
                .stream().map(i -> CommentResponseDto.getDto(i)).toList();

        Long totalComments = commentService.totalOfCommentsToBlog(blog_id);

        CommentPageDto result = new CommentPageDto(totalComments,
                (int) Math.ceilDiv(totalComments, size),
                page,
                comments);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addComment(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable ObjectId blog_id,
            @RequestBody CommentDto commentDto){
        Blog blog = blogService.getBlogById(blog_id);
        Comment comment = commentDto.getComment();

        //if user didnt give name we use username
        if(commentDto.getUsername() == null || commentDto.getUsername().isEmpty()){
            comment.setUsername(jwtService.extractUsernameFromAuthHeader(authHeader));
        }
        comment.setBlogId(blog.getId());
        blog.addComment(comment);
        blogService.saveOrUpdateBlog(blog);
        commentService.saveOrUpdateComment(comment);

        return ResponseEntity.ok(CommentResponseDto.getDto(comment));
    }


    @DeleteMapping("/{comment_id}")
    public ResponseEntity<?> deleteComment(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable ObjectId blog_id,
            @PathVariable ObjectId comment_id){
        String username = jwtService.extractUsernameFromAuthHeader(authHeader);
        Comment comment = commentService.getCommentById(comment_id);
        if(comment.getUsername().compareTo(username)==0){
            Blog blog = blogService.getBlogById(blog_id);
            blog.deleteComment(comment);
            blogService.saveOrUpdateBlog(blog);
            commentService.deleteCommentById(comment_id);
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.ok("Its not your comment, u cant delete it");



    }


}
