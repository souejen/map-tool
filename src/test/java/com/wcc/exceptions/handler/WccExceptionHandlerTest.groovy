package com.wcc.exceptions.handler

import com.wcc.entity.enums.MessageCode
import com.wcc.exceptions.NotFoundException

import spock.lang.Specification

class WccExceptionHandlerTest extends Specification {
  def wccExceptionHandler = new WccExceptionHandler()

  def "test notFound exception handling"() {
    given:
    def notFoundException = new NotFoundException("Resource not found")

    when:
    def response = wccExceptionHandler.notFound(notFoundException)

    then:
    response.code == MessageCode.NOT_FOUND
    response.message == notFoundException.message
  }

  def "test bad request exception handling"() {
    given:
    def badRequestException = new Exception("Bad request")

    when:
    def response = wccExceptionHandler.badRequest(badRequestException)

    then:
    response.code == MessageCode.BAD_REQUEST
    response.message == badRequestException.message
  }
}