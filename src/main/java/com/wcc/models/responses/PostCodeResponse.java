package com.wcc.models.responses;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCodeResponse {
    private String postcode;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
