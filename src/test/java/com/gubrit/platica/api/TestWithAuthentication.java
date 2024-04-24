package com.gubrit.platica.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static java.util.Collections.singletonList;

public abstract class TestWithAuthentication {

  @Autowired
  private OAuth2ResourceServerProperties properties;

  private String token;

  protected UUID getUserId() {
    return UUID.fromString("bfeb23c2-d9b7-4372-a376-7c79b9e59b5c");
  }

  protected String getUserToken() {

    if (token != null)
      return token;

    var headers = new HttpHeaders() {{
      setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }};

    var params = new LinkedMultiValueMap<>() {{
      put("grant_type", singletonList("password"));
      put("client_id", singletonList("platica"));
      put("username", singletonList("jperez"));
      put("password", singletonList("s3cr3t"));
    }};

    var url = properties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";
    var request = new HttpEntity<>(params, headers);

    var response = new RestTemplate().postForObject(url, request, TokenResponse.class);

    if (response == null)
      throw new RuntimeException("Failed to get token");

    return token = response.access_token;
  }

  private record TokenResponse(String access_token) {}

}
