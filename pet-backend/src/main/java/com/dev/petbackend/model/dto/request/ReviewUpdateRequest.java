package com.dev.petbackend.model.dto.request;

import lombok.Data;

@Data
public class ReviewUpdateRequest {
    private int stars;
    private String feedback;
}
