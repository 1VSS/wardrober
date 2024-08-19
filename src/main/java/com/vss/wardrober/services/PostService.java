package com.vss.wardrober.services;

import com.vss.wardrober.models.PostModel;
import com.vss.wardrober.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostModel> getAll() {
        return postRepository.findAll();
    }

    public Optional<PostModel> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public PostModel save(PostModel post) {
        return postRepository.save(post);
    }

    @Transactional
    public void deleteById(Long id2) {
        postRepository.deleteById(id2);
    }

    public List<PostModel> findPostByPiece (String piece) {
        return postRepository.findPostByPieces_name(piece);
    }
}
