package com.example.emoney.controllers;


import com.example.emoney.dtos.pageable.BlogPageDto;
import com.example.emoney.dtos.request.BlogDto;
import com.example.emoney.dtos.response.BlogResponseDto;
import com.example.emoney.enums.ComponentType;
import com.example.emoney.models.Blog;
import com.example.emoney.models.Comment;
import com.example.emoney.models.classes.Component;
import com.example.emoney.services.BlogService;
import com.example.emoney.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@CrossOrigin
public class BlogController {
    private final BlogService blogService;
    private final JwtService jwtService;


    // endpoint all for spring sec( to permit acces to unlogged users)
    @GetMapping("/all")
    public ResponseEntity<BlogPageDto> getBlogs(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size){

        Pageable pageable = PageRequest.of(page, size);

        List<Blog> blogs = blogService.getBlogsPage(pageable).stream().toList();
        long numberBlogs = blogService.getNumberOfBlogs();

        BlogPageDto response = new BlogPageDto(numberBlogs,
                (int) Math.ceilDiv(numberBlogs, pageable.getPageSize()),
                page,
                blogs.stream().map(i -> BlogResponseDto.getDto(i)).toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{blog_id}")
    public ResponseEntity<BlogResponseDto> getBlogById(@PathVariable ObjectId blog_id){

        Blog blog = blogService.getBlogById(blog_id);

        return ResponseEntity.ok(BlogResponseDto.getDto(blog));
    }


    @PostMapping
    public ResponseEntity<BlogResponseDto> addBlog(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody BlogDto blogDto){

        String username = jwtService.extractUsernameFromAuthHeader(authHeader);
        Blog blog = blogDto.getBlog(username);
        BlogResponseDto result = BlogResponseDto.getDto(blogService.saveOrUpdateBlog(blog));

        return ResponseEntity.ok(result);
    };


    @PutMapping("/{blog_id}/approve")
    public ResponseEntity<BlogResponseDto> approveBlog(@PathVariable ObjectId blog_id){

        Blog blog = blogService.getBlogById(blog_id);
        blog.setApproved(true);
        BlogResponseDto response = BlogResponseDto.getDto(blogService.saveOrUpdateBlog(blog));

        return ResponseEntity.ok(response);
    };

    @PostMapping("/data")
    public ResponseEntity<BlogResponseDto> addDefaultBlog() {

        Blog blog = new Blog();
        blog.setTitle("How to make a black coffee");
        blog.setUsername("admin");
        blog.setDescription("Here you will get knowledge how to make the best coffee jdwkjf sjf sbdjkf sjf sjkdf jksdfj ksdbf kjsdbf sjfb sf kjsf nskf nsjkf nsdjf nskjfnskjf nskjf nsjk fsjkf ");
        blog.setLastModificationDate(LocalDateTime.now());
        blog.setApproved(true);
        Component component1 = new Component(ComponentType.HEADER, "How to choose coffee");
        Component component2 = new Component(ComponentType.TEXT, "Here jir nji gjnreijg jig nerijgn ijeng ijengji neijg nreijgn ijreng jreng ijengri jneij gnejr gnijern gieng jien gjineijg neijg negnkjsnkgs ngnsj nijgn ejkgn sngkj sngjin gkjsn gldsn gjinsjig nwljndsm snfsjifn sijnf ijsnflm dsnfjksdn fjnij fwnjfkns lfmns lfnsjif nsjifn jisdnfml snf lmsnf ijsnf ijsnf ijsn fsn flnsfj nsfnsifn iusnf snfjksnfl snijgbdsib jigsdbk gndsl ndjlksndjognijsdng sng nsld ngjkslngj snijgnsdlj nsl ");
        Component component3 = new Component(ComponentType.IMAGE, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkRapDtN6JSis1bWCnMbqn3pmIEDeDY9t8tg&s");
        List<Component> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        components.add(component3);
        blog.setComponents(components);


        return ResponseEntity.ok(BlogResponseDto.getDto(blogService.saveOrUpdateBlog(blog)));
    };
}
