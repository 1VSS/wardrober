package com.vss.wardrober.controllers;

import com.vss.wardrober.DTOs.CommentDTO;
import com.vss.wardrober.DTOs.PieceDTO;
import com.vss.wardrober.DTOs.PostDTO;
import com.vss.wardrober.DTOs.UserDTO;
import com.vss.wardrober.models.CommentModel;
import com.vss.wardrober.models.PieceModel;
import com.vss.wardrober.models.PostModel;
import com.vss.wardrober.models.UserModel;
import com.vss.wardrober.services.CommentService;
import com.vss.wardrober.services.PieceService;
import com.vss.wardrober.services.PostService;
import com.vss.wardrober.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PieceService pieceService;
    private final PostService postService;
    private final CommentService commentService;

    public UserController(UserService userService, PieceService pieceService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.pieceService = pieceService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public List<UserModel> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get();
    }

    @PostMapping
    public ResponseEntity<UserModel> postUser(@Valid
                                              @RequestBody UserDTO userDTO) {

        var user = new UserModel();
        BeanUtils.copyProperties(userDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> putUser(@Valid
                                             @PathVariable Long id,
                                             @RequestBody UserDTO userDTO) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setId(user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    /*

    ============ PIECES METHODS ================

     */

    @PostMapping("/{id}/pieces")
    public ResponseEntity<PieceModel> PostUserPiece(@Valid
                                                @PathVariable Long id,
                                                @RequestBody PieceDTO pieceDTO) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        var pieceModel = new PieceModel();
        BeanUtils.copyProperties(pieceDTO, pieceModel);
        pieceModel.setUserModel(user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(pieceService.save(pieceModel));
    }

    /*

    ============ POSTS METHODS ================

     */

    @PostMapping("/{id}/posts")
    public ResponseEntity<PostModel> PostPost(@Valid
                                              @PathVariable Long id,
                                              @RequestBody PostDTO postDTO) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        var postmodel = new PostModel();

        postmodel.setPieces(pieceService.findAllById(postDTO.piecesIds()));
        postmodel.setUserModel(user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postmodel));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostModel>> getPosts(@PathVariable Long id) {
        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user.get().getPosts());
    }

    @DeleteMapping("/{id}/posts/{id2}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @PathVariable Long id2) {
        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Optional<PostModel> post = postService.findPostById(id2);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        postService.deleteById(id2);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted");
    }

    /*

    ============ COMMENTS METHODS ================

     */

    @PostMapping("/{id}/{postId}/comments")
    public ResponseEntity<CommentModel> postComment(@PathVariable Long id,
                                                    @PathVariable Long postId,
                                                    @RequestBody CommentDTO commentDTO) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Optional<PostModel> post = postService.findPostById(postId);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        var comment = new CommentModel();
        comment.setComment(commentDTO.comment());
        comment.setPostModel(post.get());
        comment.setUserModel(user.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment));

    }

    @DeleteMapping("/{id}/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id,
                                                @PathVariable Long postId,
                                                @PathVariable Long commentId) {

        Optional<UserModel> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Optional<PostModel> post = postService.findPostById(postId);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        commentService.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("Comment deleted");
    }


    
}


