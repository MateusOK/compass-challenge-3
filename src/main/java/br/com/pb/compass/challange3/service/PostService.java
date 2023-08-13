package br.com.pb.compass.challange3.service;

import br.com.pb.compass.challange3.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    void createPost(Long id);

    List<PostResponseDto> getAllPosts();

}