package com.vss.wardrober.repositories;

import com.vss.wardrober.models.PieceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<PieceModel, Long> {
}
