package com.vss.wardrober.repositories;

import com.vss.wardrober.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Long> {

    List<PostModel> findPostByPieces_name(String piece);
}
