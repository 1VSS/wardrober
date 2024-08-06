package com.vss.wardrober.repositories;

import com.vss.wardrober.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Long> {
}
