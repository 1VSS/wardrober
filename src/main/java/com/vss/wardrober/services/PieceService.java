package com.vss.wardrober.services;

import com.vss.wardrober.models.PieceModel;
import com.vss.wardrober.repositories.PieceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PieceService {

    private final PieceRepository pieceRepository;

    public PieceService(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    public List<PieceModel> getAll() {
        return pieceRepository.findAll();
    }

    public Optional<PieceModel> findById(Long id) {
        return pieceRepository.findById(id);
    }

    @Transactional
    public PieceModel save(PieceModel pieceModel) {
        return pieceRepository.save(pieceModel);
    }

    @Transactional
    public void deleteById(Long id) {
        pieceRepository.deleteById(id);
    }

    public List<PieceModel> findAllById(List<Long> ids) {
        return pieceRepository.findAllById(ids);
    }
}

