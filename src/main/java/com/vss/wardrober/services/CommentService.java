package com.vss.wardrober.services;

import com.vss.wardrober.models.CommentModel;
import com.vss.wardrober.models.PostModel;
import com.vss.wardrober.repositories.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentModel> findAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public CommentModel save(CommentModel comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
