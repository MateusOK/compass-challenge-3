package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.exception.ResourceNotFoundException;
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
public class PostFindQueue {

    private final JmsTemplate jmsTemplate;
    private final PostRepository postRepository;
    private final ExternalApi externalApi;

    @JmsListener(destination = "post.find.queue")
    public void processFindPost(Long postId) {

        Mono<Post> postMono = externalApi.fetchPostById(postId);
        if (postMono == null) {
            jmsTemplate.convertAndSend("failed.queue", postId);
        } else {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found" + postId));
            post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.POST_FIND.name(), post));
            postRepository.save(post);
            jmsTemplate.convertAndSend("post.ok.queue", postId);
        }
    }
}