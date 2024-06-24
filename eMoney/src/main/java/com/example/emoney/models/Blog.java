package com.example.emoney.models;

import com.example.emoney.models.classes.Component;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "blogs")
@Data
public class Blog {
    @Id
    private ObjectId id;

    private String username;

    private String description;

    private String title;

    private String image_link;

    @Field("date_modification")
    private LocalDateTime lastModificationDate;

    @DBRef
    private List<Comment> comments = new ArrayList<>();

    private List<Component> components = new ArrayList<>();

    private Boolean approved;

    public void addComponent(Component component) {
            components.add(component);
    }
    public void deleteComponent(Component component) {
        components.remove(component);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }
    public void deleteComment(Comment comment){
        comments.remove(comment);
    }
    public Blog(){
        this.id = new ObjectId();
    }


}
