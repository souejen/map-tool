package com.wcc.services;

import com.wcc.entity.Ukpostcode;
import com.wcc.entity.enums.DistanceUnit;
import com.wcc.exceptions.NotFoundException;
import com.wcc.models.dtos.CoordinateDto;
import com.wcc.models.responses.DistanceResponse;
import com.wcc.models.responses.PostCodeResponse;
import com.wcc.repositories.UkpostcodeRepository;
import com.wcc.services.impl.HaversineServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DistanceService {
    @Autowired
    private HaversineServiceImpl haversineService;

    @Autowired
    private UkpostcodeRepository ukpostcodeRepository;

    @Transactional
    public DistanceResponse retrieveDistance(String postcode1, String postcode2) {
        Ukpostcode coords1 = ukpostcodeRepository.findByPostcode(postcode1).orElse(null);
        Ukpostcode coords2 = ukpostcodeRepository.findByPostcode(postcode2).orElse(null);

        validatePostcodes(postcode1, postcode2, coords1, coords2);

        double distance = haversineService.calculateDistance(coords1.getLat().doubleValue(), coords1.getLng().doubleValue(),
                coords2.getLat().doubleValue(), coords2.getLng().doubleValue());

        PostCodeResponse location1Response = mapToPostCodeResponse(coords1);
        PostCodeResponse location2Response = mapToPostCodeResponse(coords2);

        return DistanceResponse.builder().distance(distance).unit(DistanceUnit.KM.getLabel()).location1(location1Response).location2(location2Response).build();
    }

    private void validatePostcodes(String postcode1, String postcode2, Ukpostcode coords1, Ukpostcode coords2) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(coords1)) {
            errors.add("Invalid post code 1" + postcode1);
        }
        if (Objects.isNull(coords2)) {
            errors.add("Invalid post code 2" + postcode2);
        }
        if (!errors.isEmpty()) {
            throw new NotFoundException("Postcode(s) not found: " + errors);
        }
    }

    protected PostCodeResponse mapToPostCodeResponse(Ukpostcode location) {
        return PostCodeResponse.builder().postcode(location.getPostcode()).longitude(location.getLng()).latitude(location.getLat()).build();
    }

    public String updateCoordinateByPostalCode(String postalCode, CoordinateDto coordinate) {
        Ukpostcode coord = ukpostcodeRepository.findByPostcode(postalCode)
                .orElseThrow(() -> new NotFoundException("Post code " + postalCode + " not found."));

        coord.setLat(coordinate.getLatitude());
        coord.setLng(coordinate.getLongitude());
        ukpostcodeRepository.save(coord);

        log.info("Updated postalCode: postcode={}, coordinate={}", postalCode, coordinate);
        return postalCode;
    }
}
