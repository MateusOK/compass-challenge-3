package br.com.pb.compass.challange3.service;

import br.com.pb.compass.challange3.entity.Comment;
import br.com.pb.compass.challange3.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExternalApi {

    Flux<Comment> fetchCommentForPost(Long postId);

    Mono<Post> fetchPostById(Long postId);

}
