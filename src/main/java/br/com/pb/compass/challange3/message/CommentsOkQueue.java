package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.entity.Comment;
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
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentsOkQueue {

    private final JmsTemplate jmsTemplate;
    private final PostRepository postRepository;
    private final ExternalApi externalApi;

    @JmsListener(destination = "comments.ok.queue")
    public void processOkComments(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found" + postId));
        Flux<Comment> commentFlux = externalApi.fetchCommentForPost(postId);

        commentFlux.collectList().subscribe(
                commentList -> {
                    commentList.forEach(comment -> comment.setPostId(postId));
                    post.getComments().clear();
                    post.getComments().addAll(commentList);

                    post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.COMMENTS_OK.name(), post));

                    postRepository.save(post);

                    jmsTemplate.convertAndSend("enabled.queue", post.getId());
                }
        );
    }
}