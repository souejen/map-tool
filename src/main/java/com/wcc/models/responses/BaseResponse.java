package com.wcc.models.responses;

import com.wcc.entity.enums.MessageCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private MessageCode code;
    private String message;
}
