package com.wcc.models.dtos;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoordinateDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
