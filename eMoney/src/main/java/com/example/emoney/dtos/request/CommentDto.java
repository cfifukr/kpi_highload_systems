package com.example.emoney.dtos.request;

import com.example.emoney.models.Comment;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private String text;
    private String username;

    public Comment getComment(){
        Comment comment = new Comment();
        comment.setId(new ObjectId());
        comment.setUsername(this.username);
        comment.setText(this.text);
        comment.setLocalDateTime(LocalDateTime.now());
        System.out.println(comment);
        return comment;
    }

}
