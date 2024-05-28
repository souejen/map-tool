package com.wcc.controllers;

import com.wcc.models.dtos.CoordinateDto;
import com.wcc.models.responses.DistanceResponse;
import com.wcc.services.DistanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/api")
@RestController
public class UkpostcodeController {
    @Autowired
    private DistanceService distanceService;

    @GetMapping("/distance")
    public ResponseEntity<DistanceResponse> calculateDistance(@RequestParam("postcode1") String postcode1,
                                                              @RequestParam("postcode2") String postcode2) {
        log.info("Processing distance request: postcode1={}, postcode2={}", postcode1, postcode2);

        return ResponseEntity.ok(distanceService.retrieveDistance(postcode1, postcode2));
    }

    @PutMapping("/postal-codes/{postalCode}")
    public ResponseEntity<String> updateCoordinateByPostalCode(@PathVariable String postalCode,
                                                                         @RequestBody CoordinateDto coordinate) {

        log.info("Updating postalCode: postcode={}, coordinate={}", postalCode, coordinate);
        return ResponseEntity.ok(distanceService.updateCoordinateByPostalCode(postalCode, coordinate));
    }
}