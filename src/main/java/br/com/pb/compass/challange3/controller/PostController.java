package br.com.pb.compass.challange3.controller;

import br.com.pb.compass.challange3.dto.PostResponseDto;
import br.com.pb.compass.challange3.service.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final JmsTemplate jmsTemplate;
    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> processPost(@PathVariable @Positive @Max(100) @Min(1) Long postId){
        jmsTemplate.convertAndSend("created.queue", postId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> disablePost(@PathVariable @Positive @Max(100) @Min(1) Long postId){
        jmsTemplate.convertAndSend("disabled.queue", postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable @Positive @Max(100) @Min(1) Long postId){
        postService.updatePost(postId);
        return ResponseEntity.accepted().build();
    }
}