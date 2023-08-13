package br.com.pb.compass.challange3.message;


import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.repository.PostRepository;
import br.com.pb.compass.challange3.service.ExternalApi;
import br.com.pb.compass.challange3.utils.HistoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostOkQueue {
    private final JmsTemplate jmsTemplate;
    private final PostRepository postRepository;
    private final ExternalApi externalApi;

    @JmsListener(destination = "post.ok.queue")
    public void processOkPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found " + postId));
        Mono<Post> postMono = externalApi.fetchPostById(postId);

        postMono.subscribe(
                postFromExternalApi -> {
                    post.setTitle(postFromExternalApi.getTitle());
                    post.setBody(postFromExternalApi.getBody());

                    post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.POST_OK.name(), post));

                    postRepository.save(post);

                    jmsTemplate.convertAndSend("comments.find.queue", post.getId());
                }
        );
    }
}