package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateQueue {

    private final PostService postService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "created.queue")
    public void processCreatePost(Long postId){
        postService.createPost(postId);
        jmsTemplate.convertAndSend("post.find.queue", postId);
    }

}
