package com.vss.wardrober.controllers;

import com.vss.wardrober.DTOs.PieceDTO;
import com.vss.wardrober.models.PieceModel;
import com.vss.wardrober.services.PieceService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pieces")
public class PieceController {

    private final PieceService pieceService;

    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @GetMapping
    public List<PieceModel> getPieces() {
        return pieceService.getAll();
    }

    @GetMapping("/{id}")
    public PieceModel getPiece(@PathVariable Long id) {
        Optional<PieceModel> piece = pieceService.findById(id);
        if (piece.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "piece not found");
        }
        return piece.get();
    }

    @PostMapping
    public ResponseEntity<PieceModel> postPiece(@Valid
                                                @RequestBody PieceDTO pieceDTO) {
        var pieceModel = new PieceModel();
        BeanUtils.copyProperties(pieceDTO, pieceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pieceService.save(pieceModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePiece(@PathVariable Long id) {
        Optional<PieceModel> piece = pieceService.findById(id);
        if (piece.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "piece not found");
        }
        pieceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Piece Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceModel> putPiece(@Valid
                                               @PathVariable Long id,
                                               @RequestBody PieceDTO pieceDTO) {

        Optional<PieceModel> piece = pieceService.findById(id);
        if (piece.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "piece not found");
        }
        var pieceModel = new PieceModel();
        BeanUtils.copyProperties(pieceDTO, pieceModel);
        pieceModel.setId(piece.get().getId());
        pieceModel.setUserModel(piece.get().getUserModel());
        return ResponseEntity.status(HttpStatus.OK).body(pieceService.save(pieceModel));

    }
}
