package io.pivotal.retrobox.acceptance.common

import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.parsing.Parser
import com.jayway.restassured.specification.RequestSpecification

import static com.jayway.restassured.RestAssured.given
import static com.jayway.restassured.config.DecoderConfig.decoderConfig
import static com.jayway.restassured.config.EncoderConfig.encoderConfig
import static com.jayway.restassured.config.RestAssuredConfig.newConfig

class BaseApiClient {

  static final int DEFAULT_HTTP_PORT = 8080
  static final String DEFAULT_HOST = 'http://localhost'

  static RequestSpecification givenApiClient() {
    String port = getPort()
    String baseUrl = getBaseUrl() + getContext()
    def config = newConfig()
      .encoderConfig(encoderConfig().defaultContentCharset('utf-8'))
      .decoderConfig(decoderConfig().defaultContentCharset('utf-8'))

    RestAssured.defaultParser = Parser.JSON

    given().contentType(ContentType.JSON).port(port as int)
      .config(config)
      .baseUri(baseUrl)
      .log().all()
  }

  static String getApiClientUrl() {
    getBaseUrl() + ':' + getPort() + getContext()
  }

  private static String getPort() {
    System.getProperty('api.server.port', DEFAULT_HTTP_PORT as String)
  }

  private static getBaseUrl() {
    System.getProperty('api.server.baseUri', DEFAULT_HOST)
  }

  private static getContext() {
    return System.getProperty('api.server.context', '/')
  }

}
