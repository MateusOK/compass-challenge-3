package br.com.pb.compass.challange3.message;

import br.com.pb.compass.challange3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnabledQueue {

    private final PostService postService;

    @JmsListener(destination = "enabled.queue")
    public void processEnablePost(Long postId) {
            postService.enablePost(postId);
    }
}