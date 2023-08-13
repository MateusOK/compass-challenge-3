package br.com.pb.compass.challange3.service.impl;

import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.repository.PostRepository;
import br.com.pb.compass.challange3.service.PostService;
import br.com.pb.compass.challange3.utils.HistoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(Long postId) {
        Post post = new Post();
        post.setId(postId);
        post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.CREATED.name(), post));
        postRepository.save(post);
    }
}
