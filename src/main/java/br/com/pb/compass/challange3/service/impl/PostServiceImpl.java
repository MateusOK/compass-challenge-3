package br.com.pb.compass.challange3.service.impl;

import br.com.pb.compass.challange3.dto.PostResponseDto;
import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.repository.PostRepository;
import br.com.pb.compass.challange3.service.PostService;
import br.com.pb.compass.challange3.utils.HistoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<PostResponseDto> getAllPosts() {
        var posts = postRepository.findAll();
        var postsDto = new ArrayList<PostResponseDto>();
        posts.forEach(post -> postsDto.add(new PostResponseDto(post)));
        if (posts.isEmpty()){
            throw new RuntimeException("No posts were found");
        }
        return postsDto;
    }

    @Override
    public void failPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
        post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.FAILED.name(), post));
        postRepository.save(post);
    }

    @Override
    public void enablePost(Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()){
            Post post = postOptional.get();
            post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.ENABLED.name(), post));
            postRepository.save(post);
        }
    }

    @Override
    public void disablePost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            List<History> historyList = post.getHistory();
            if (historyList.isEmpty()) {
                throw new IllegalStateException("Post history is empty.");
            }

            History latestHistory = historyList.get(historyList.size() - 1);
            if (latestHistory.getStatus().equals(HistoryStatus.ENABLED.name())) {
                post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.DISABLED.name(), post));
                postRepository.save(post);
            } else if (latestHistory.getStatus().equals(HistoryStatus.DISABLED.name())) {
                throw new RuntimeException("Post is already disabled");
            } else {
                throw new IllegalStateException("Latest history is not in the ENABLED state.");
            }
        } else {
            throw new RuntimeException("Post not found.");
        }
    }
}