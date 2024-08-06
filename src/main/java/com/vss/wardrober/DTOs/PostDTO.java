package com.vss.wardrober.DTOs;

import com.vss.wardrober.models.CommentModel;

import java.util.List;

public record PostDTO(
        //int likes,
        //List<CommentModel> comments,
        List<Long> piecesIds
) {
}
