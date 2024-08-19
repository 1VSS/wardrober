package com.vss.wardrober.controllers;

import com.vss.wardrober.models.PostModel;
import com.vss.wardrober.services.CommentService;
import com.vss.wardrober.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(CommentService commentService, PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostModel> getPosts() {
        return postService.getAll();
    }

    @GetMapping("/search")
    public List<PostModel> GetPostsByPiece(@RequestParam String piece) {
        return postService.findPostByPiece(piece);
    }

}
