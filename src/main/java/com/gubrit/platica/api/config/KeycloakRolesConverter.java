package com.gubrit.platica.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class KeycloakRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  public static final String PREFIX_RESOURCE_ROLE = "ROLE_";

  private static final String CLAIM_RESOURCE_ACCESS = "resource_access";

  private static final String CLAIM_ROLES = "roles";

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim(CLAIM_RESOURCE_ACCESS);
    if (resourceAccess != null && !resourceAccess.isEmpty()) {
      resourceAccess.forEach(
          (resource, resourceClaims) -> resourceClaims.get(CLAIM_ROLES).forEach(
              role -> grantedAuthorities.add(new SimpleGrantedAuthority(PREFIX_RESOURCE_ROLE + role))
          )
      );
    }
    return grantedAuthorities;
  }

}