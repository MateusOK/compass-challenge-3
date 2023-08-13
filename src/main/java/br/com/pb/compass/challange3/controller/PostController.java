package br.com.pb.compass.challange3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> processPost(@PathVariable Long postId){
        jmsTemplate.convertAndSend("created.queue", postId);
        return ResponseEntity.accepted().build();
    }
}
