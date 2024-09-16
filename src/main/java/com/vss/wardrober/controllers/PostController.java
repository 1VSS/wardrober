package com.vss.wardrober.controllers;

import com.vss.wardrober.DTOs.PostDTO;
import com.vss.wardrober.models.PostModel;
import com.vss.wardrober.models.UserModel;
import com.vss.wardrober.services.CommentService;
import com.vss.wardrober.services.PieceService;
import com.vss.wardrober.services.PostService;
import com.vss.wardrober.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PieceService pieceService;
    private final UserService userService;

    public PostController(CommentService commentService, PostService postService, PieceService pieceService, UserService userService) {
        this.postService = postService;
        this.pieceService = pieceService;
        this.userService = userService;
    }

    @GetMapping
    public List<PostModel> getPosts() {
        return postService.getAll();
    }

    @GetMapping("/search")
    public List<PostModel> GetPostsByPiece(@RequestParam String piece) {
        return postService.findPostByPiece(piece);
    }

    @PostMapping("/")
    public ResponseEntity<PostModel> PostPost(@Valid
                                              @RequestBody PostDTO postDTO,
                                              JwtAuthenticationToken token) {

        var user = userService.findById(Long.parseLong(token.getName()));
        var postmodel = new PostModel();

        postmodel.setPieces(pieceService.findAllById(postDTO.piecesIds()));
        postmodel.setUserModel(user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postmodel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostModel>> getPosts(@PathVariable Long id) {
        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user.get().getPosts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
                                             JwtAuthenticationToken token) {

        Optional<PostModel> post = postService.findPostById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        if (post.get().getUserModel().getId().toString().equals(token.getName())) {
            postService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Post deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
