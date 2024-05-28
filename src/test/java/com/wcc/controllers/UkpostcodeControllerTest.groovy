package com.wcc.controllers

import com.wcc.models.dtos.CoordinateDto
import com.wcc.services.DistanceService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class UkpostcodeControllerTest extends Specification {

  private UkpostcodeController controller
  private DistanceService distanceService

  void setup() {
    distanceService = Mock(DistanceService)
    controller = new UkpostcodeController(distanceService: distanceService)
  }

  def "get retrieveDistance"() {
    given:
    def postcode1 = "postcode1"
    def postcode2 = "postcode2"

    when:
    def response = controller.calculateDistance(postcode1, postcode2)

    then:
    1 * distanceService.retrieveDistance(postcode1, postcode2)
    response.statusCode == HttpStatus.OK
  }

  def "update postal-codes"() {
    given:
    def postcode1 = "postcode1"
    CoordinateDto coordinateDto = new CoordinateDto()

    when:
    def response = controller.updateCoordinateByPostalCode(postcode1, coordinateDto)

    then:
    1 * distanceService.updateCoordinateByPostalCode(postcode1, coordinateDto)
    response.statusCode == HttpStatus.OK
  }
}
