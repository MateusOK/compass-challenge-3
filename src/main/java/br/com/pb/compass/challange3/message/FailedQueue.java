package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FailedQueue {

    private final JmsTemplate jmsTemplate;
    private final PostService postService;

    @JmsListener(destination = "failed.queue")
    public void processFailed(Long postId){
            postService.failPost(postId);
            jmsTemplate.convertAndSend("disabled.queue", postId);
       }
    }