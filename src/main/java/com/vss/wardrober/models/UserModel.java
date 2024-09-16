package com.vss.wardrober.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vss.wardrober.DTOs.LoginRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "TB_USERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonManagedReference
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<PieceModel> pieces;
    @JsonManagedReference
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<PostModel> posts;

    public UserModel(Long id, String username, String password, String email, List<PieceModel> pieces, List<PostModel> posts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pieces = pieces;
        this.posts = posts;
    }

    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PieceModel> getPieces() {
        return pieces;
    }

    public void setPieces(List<PieceModel> pieces) {
        this.pieces = pieces;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, PasswordEncoder passwordEncoder) {

        return passwordEncoder.matches(loginRequestDTO.password(), this.password);

    }
}
