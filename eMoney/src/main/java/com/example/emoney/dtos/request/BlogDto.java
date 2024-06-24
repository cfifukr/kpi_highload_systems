package com.example.emoney.dtos.request;

import com.example.emoney.enums.ComponentType;
import com.example.emoney.models.Blog;
import com.example.emoney.models.classes.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {

    private String title;

    private String username;

    private String image_link;

    private String description;

    private List<ComponentDto> components = new ArrayList<>();

    public Blog getBlog(String username){
        Blog blog = new Blog();

        blog.setUsername(this.username);
        blog.setTitle(this.title);
        blog.setDescription(this.description);
        blog.setImage_link(this.image_link);
        blog.setLastModificationDate(LocalDateTime.now());
        blog.setApproved(false);

        for (ComponentDto componentDto : this.getComponents()) {
            Component component = new Component();
            component.setType(ComponentType.valueOf(componentDto.getType()));
            component.setData(componentDto.getData());
            blog.addComponent(component);
        }
        return blog;
    }

}
