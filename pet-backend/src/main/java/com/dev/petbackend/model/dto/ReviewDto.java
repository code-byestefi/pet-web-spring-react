package com.dev.petbackend.model.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String stars;
    private String feedback;
}
