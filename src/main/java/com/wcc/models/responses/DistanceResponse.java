package com.wcc.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistanceResponse {
    private PostCodeResponse location1;
    private PostCodeResponse location2;
    private double distance;
    private String unit;
}
