package com.example.emoney.dtos.response;

import com.example.emoney.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponseDto {
    private String id;

    private String username;

    private String text;

    private LocalDateTime creationDate;

    public static CommentResponseDto getDto(Comment comment){
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId().toString());
        commentResponseDto.setText(comment.getText());
        commentResponseDto.setUsername(comment.getUsername());
        commentResponseDto.setCreationDate(comment.getLocalDateTime());
        return commentResponseDto;
    }
}
