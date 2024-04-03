package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:validation.properties")
public final class TokenProperties {

  @Value("${token.secret}")
  public String SECRET;

  @Value("${token.duration}")
  public int DURATION;

}
