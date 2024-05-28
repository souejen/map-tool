package com.wcc.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageCode {

    /* Success start */
    SUCCESS("S001"),
    /* Success end */

    /* Error start */
    NOT_FOUND("E001"),
    BAD_REQUEST("E002");
    /* Error end */;

    private String code;

    @JsonValue
    public String getCode() {
        return code;
    }
}
