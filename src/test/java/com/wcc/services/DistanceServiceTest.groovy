package com.wcc.services

import com.wcc.entity.Ukpostcode
import com.wcc.entity.enums.DistanceUnit
import com.wcc.exceptions.NotFoundException
import com.wcc.models.dtos.CoordinateDto
import com.wcc.models.responses.PostCodeResponse
import com.wcc.repositories.UkpostcodeRepository
import com.wcc.services.impl.HaversineServiceImpl
import spock.lang.Specification

class DistanceServiceTest extends Specification {

  private DistanceService service
  private HaversineServiceImpl haversineService
  private UkpostcodeRepository ukpostcodeRepository

  def location1 = Ukpostcode.builder().id(1)
      .postcode("AB10 1XG").lat(57.1441560).lng(-2.1148640)
      .build()
  def location2 = Ukpostcode.builder().id(1)
      .postcode("AB10 6RN").lat(57.1378710).lng(-2.1214870)
      .build()

  def setup() {
    haversineService = Mock(HaversineServiceImpl)
    ukpostcodeRepository = Mock(UkpostcodeRepository)
    service = new DistanceService(
        haversineService: haversineService,
        ukpostcodeRepository: ukpostcodeRepository
    )

    ukpostcodeRepository.findByPostcode(location1.getPostcode()) >> Optional.of(location1)
    ukpostcodeRepository.findByPostcode(location2.getPostcode()) >> Optional.of(location2)
  }

  def "test get calculateDistance"() {
    when:
    def result = service.retrieveDistance(location1.getPostcode(), location2.getPostcode())

    then:
    1 * ukpostcodeRepository.findByPostcode(location1.getPostcode()) >> Optional.of(location1)
    1 * ukpostcodeRepository.findByPostcode(location2.getPostcode()) >> Optional.of(location2)
    1 * haversineService.calculateDistance(_, _, _, _) >> 0.8050251643085133

    BigDecimal.valueOf(result.distance) == 0.8050251643085133
    result.unit == DistanceUnit.KM.getLabel()
    result.location1 == PostCodeResponse.builder().postcode(location1.getPostcode()).latitude(location1.getLat()).longitude(location1.getLng()).build()
    result.location2 == PostCodeResponse.builder().postcode(location2.getPostcode()).latitude(location2.getLat()).longitude(location2.getLng()).build()
  }

  def "test get calculateDistance, throw not found "() {
    given:
    def mockLocation1 = "11"
    def mockLocation2 = "22"
    ukpostcodeRepository.findByPostcode(mockLocation1) >> Optional.empty()
    ukpostcodeRepository.findByPostcode(mockLocation2) >> Optional.empty()

    when:
    service.retrieveDistance(mockLocation1, mockLocation2)

    then:
    2 * ukpostcodeRepository.findByPostcode(_) >> Optional.empty()
    thrown NotFoundException
  }

  def "test update postal code, throw not found "() {
    given:
    CoordinateDto coordinateDto = CoordinateDto.builder().longitude(58.1441560).latitude(-2.1148641).build();

    when:
    service.updateCoordinateByPostalCode(location1.getPostcode(), coordinateDto)

    then:
    1 * ukpostcodeRepository.findByPostcode(location1.getPostcode()) >> Optional.empty()
    thrown NotFoundException
  }

  def "test update postal code"() {
    given:
    CoordinateDto coordinateDto = CoordinateDto.builder().longitude(58.1441560).latitude(-2.1148641).build();

    when:
    def result = service.updateCoordinateByPostalCode(location1.getPostcode(), coordinateDto)

    then:
    1 * ukpostcodeRepository.findByPostcode(location1.getPostcode()) >> Optional.of(location1)
    1 * ukpostcodeRepository.save(_)
    result == location1.getPostcode()
  }

  def "map to post code response"() {
    when:
    def result = service.mapToPostCodeResponse(location1)

    then:
    result.getPostcode() == location1.getPostcode()
    result.getLongitude() == location1.getLng()
    result.getLatitude() == location1.getLat()
  }
}
