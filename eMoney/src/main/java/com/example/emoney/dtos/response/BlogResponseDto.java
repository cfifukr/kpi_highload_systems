package com.example.emoney.dtos.response;

import com.example.emoney.models.Blog;
import com.example.emoney.models.classes.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseDto{

    private String id;
    private String username;
    private String title;
    private String description;
    private String image_link;
    private LocalDateTime lastModificationDate;
    private List<Component> components;
    private Boolean approved;

    public static BlogResponseDto getDto(Blog blog) {
        BlogResponseDto result = BlogResponseDto.builder()
                .id(blog.getId().toString())
                .title(blog.getTitle())
                .username(blog.getUsername())
                .description(blog.getDescription())
                .image_link(blog.getImage_link())
                .lastModificationDate(blog.getLastModificationDate())
                .components(blog.getComponents())
                .approved(blog.getApproved())
                .build();
        return result;
    }
}
