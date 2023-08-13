package br.com.pb.compass.challange3.dto;

import br.com.pb.compass.challange3.entity.Comment;
import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;

import java.util.List;

public record PostResponseDto(
        Long id,
        String title,
        String body,
        List<Comment> comments,
        List<History> history
) {
    public PostResponseDto(Post response){
        this(response.getId(), response.getTitle(), response.getBody(),
                response.getComments(), response.getHistory());
    }
}
