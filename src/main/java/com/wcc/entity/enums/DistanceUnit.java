package com.wcc.entity.enums;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DistanceUnit implements Serializable {
    KM("km"),
    METRE("m");

    private final String label;
}
