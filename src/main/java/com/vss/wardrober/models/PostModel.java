package com.vss.wardrober.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_POSTS")
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int likes;
//    @OneToMany(mappedBy = "postModel", cascade = CascadeType.ALL)
//    private List<CommentModel> comments;
    @ManyToMany
    @JoinTable(
            name = "posts_pieces",
            joinColumns = @JoinColumn(name = "piece_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<PieceModel> pieces;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

//    public List<CommentModel> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<CommentModel> comments) {
//        this.comments = comments;
//    }


    public List<PieceModel> getPieces() {
        return pieces;
    }

    public void setPieces(List<PieceModel> pieces) {
        this.pieces = pieces;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
