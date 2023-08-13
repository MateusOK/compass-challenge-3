package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DisabledQueue {

    private final PostService postService;

    @JmsListener(destination = "disabled.queue")
    public void processDisablePost(Long postId) {
        postService.disablePost(postId);
    }
}
