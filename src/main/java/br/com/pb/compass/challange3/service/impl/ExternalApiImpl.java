package br.com.pb.compass.challange3.service.impl;

import br.com.pb.compass.challange3.entity.Comment;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.service.ExternalApi;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiImpl implements ExternalApi {

    private final WebClient webClient;

    public ExternalApiImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    @Override
    public Mono<Post> fetchPostById(Long postId){
        return webClient.get()
                .uri("/posts/{postId}", postId)
                .retrieve()
                .bodyToMono(Post.class);
    }
    @Override
    public Flux<Comment> fetchCommentForPost(Long postId){
        return webClient.get()
                .uri("/posts/{postId}/comments", postId)
                .retrieve()
                .bodyToFlux(Comment.class);
    }


}