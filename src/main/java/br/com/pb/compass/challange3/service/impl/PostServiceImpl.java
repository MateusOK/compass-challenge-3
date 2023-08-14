package br.com.pb.compass.challange3.service.impl;

import br.com.pb.compass.challange3.dto.PostResponseDto;
import br.com.pb.compass.challange3.entity.History;
import br.com.pb.compass.challange3.entity.Post;
import br.com.pb.compass.challange3.exception.PostAlreadyExistsException;
import br.com.pb.compass.challange3.exception.ResourceNotFoundException;
import br.com.pb.compass.challange3.repository.PostRepository;
import br.com.pb.compass.challange3.service.PostService;
import br.com.pb.compass.challange3.utils.HistoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final JmsTemplate jmsTemplate;

    @Override
    public void createPost(Long postId) {
        if (isPostAlreadyExists(postId)){
            throw new PostAlreadyExistsException("Post with id " + postId + " already exists");
        }
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
            throw new ResourceNotFoundException("No posts were found");
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
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.ENABLED.name(), post));
        postRepository.save(post);
    }

    @Override
    public void disablePost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        List<History> historyList = post.getHistory();
        if (historyList.isEmpty()) {
            throw new IllegalStateException("Post history is empty.");
        }

        History latestHistory = historyList.get(historyList.size() - 1);
        if (latestHistory.getStatus().equals(HistoryStatus.ENABLED.name())) {
            post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.DISABLED.name(), post));
            postRepository.save(post);
        } else if (latestHistory.getStatus().equals(HistoryStatus.DISABLED.name())) {
            throw new IllegalStateException("Post is already disabled");
        } else {
            throw new IllegalStateException("Latest history is not in the ENABLED state.");
        }
    }

    @Override
    public void updatePost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        if (isPostInEnabledOrDisabledState(post)) {
            post.getHistory().add(new History(LocalDateTime.now(), HistoryStatus.UPDATING.name(), post));

            postRepository.save(post);
            jmsTemplate.convertAndSend("post.find.queue", postId);
        }
    }

    private boolean isPostInEnabledOrDisabledState(Post post) {
        if (post != null && post.getHistory() != null && !post.getHistory().isEmpty()) {
            String latestStatus = post.getHistory().get(post.getHistory().size() - 1).getStatus();
            return latestStatus.equals(HistoryStatus.ENABLED.name()) || latestStatus.equals(HistoryStatus.DISABLED.name());
        }
        return false;
    }

    private boolean isPostAlreadyExists(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post.isPresent();
    }
}