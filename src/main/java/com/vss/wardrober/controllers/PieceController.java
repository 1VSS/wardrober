package com.vss.wardrober.controllers;

import com.vss.wardrober.DTOs.PieceDTO;
import com.vss.wardrober.models.PieceModel;
import com.vss.wardrober.services.PieceService;
import com.vss.wardrober.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pieces")
public class PieceController {

    private final PieceService pieceService;
    private final UserService userService;

    public PieceController(PieceService pieceService, UserService userService) {
        this.pieceService = pieceService;
        this.userService = userService;
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
                                                @RequestBody PieceDTO pieceDTO,
                                                JwtAuthenticationToken token) {

        var user = userService.findById(Long.parseLong(token.getName()));

        var pieceModel = new PieceModel();
        BeanUtils.copyProperties(pieceDTO, pieceModel);
        pieceModel.setUserModel(user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(pieceService.save(pieceModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePiece(@PathVariable Long id,
                                              JwtAuthenticationToken token) {


        Optional<PieceModel> piece = pieceService.findById(id);
        if (piece.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "piece not found");
        }

        if (piece.get().getUserModel().getId().toString().equals(token.getName())) {
            pieceService.deleteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body("Piece Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceModel> putPiece(@Valid
                                               @PathVariable Long id,
                                               @RequestBody PieceDTO pieceDTO,
                                               JwtAuthenticationToken token) {

        Optional<PieceModel> piece = pieceService.findById(id);
        if (piece.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "piece not found");
        }
        if (piece.get().getUserModel().getId().toString().equals(token.getName())) {
            var pieceModel = new PieceModel();
            BeanUtils.copyProperties(pieceDTO, pieceModel);
            pieceModel.setId(piece.get().getId());
            pieceModel.setUserModel(piece.get().getUserModel());
            return ResponseEntity.status(HttpStatus.OK).body(pieceService.save(pieceModel));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }
}
