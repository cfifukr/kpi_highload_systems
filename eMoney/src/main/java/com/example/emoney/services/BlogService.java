package com.example.emoney.services;

import com.example.emoney.models.Blog;
import com.example.emoney.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public Blog saveOrUpdateBlog(Blog blog){
        return blogRepository.save(blog);
    }

    @Transactional
    public void deleteBlogById(ObjectId id){
        blogRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Blog getBlogById(ObjectId id){
        Optional<Blog> blog = blogRepository.findById(id);
        return  blog.orElse(null);
    }


    @Transactional
    public long  getNumberOfBlogs(){
        return blogRepository.count();
    }

    @Transactional
    public Page<Blog> getBlogsPage(Pageable pageable){
        return blogRepository.findAll(pageable);
    }

}
