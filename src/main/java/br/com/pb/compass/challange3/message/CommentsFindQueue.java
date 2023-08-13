package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.entity.Comment;
import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
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
public class CommentsFindQueue {

    private final PostRepository postRepository;
    private final ExternalApi externalApi;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "comments.find.queue")
    public void processFindComments(Long postId){
        Flux<Comment> comments = externalApi.fetchCommentForPost(postId);
        if (comments == null){
            jmsTemplate.convertAndSend("failed.queue", postId);
        }
        else {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found" + postId));
            post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.COMMENTS_FIND.name(), post));
            postRepository.save(post);
            jmsTemplate.convertAndSend("comments.ok.queue", post.getId());
            }
        }
    }