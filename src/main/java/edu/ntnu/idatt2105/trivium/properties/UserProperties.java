package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:validation.properties")
public final class UserProperties {

  // Username
  @Value("${user.name.empty}")
  public String NAME_EMPTY;
  @Value("${user.name.len.min}")
  public int NAME_LEN_MIN;
  @Value("${user.name.len.max}")
  public int NAME_LEN_MAX;
  @Value("${user.name.len.message}")
  public String NAME_LEN_MSG;
  @Value("${user.name.regex}")
  public String NAME_REGEX;
  @Value("${user.name.regex.message}")
  public String NAME_REGEX_MSG;

  // Password
  @Value("${user.pass.empty}")
  public String PASS_EMPTY;
  @Value("${user.pass.len.min}")
  public int PASS_LEN_MIN;
  @Value("${user.pass.len.max}")
  public int PASS_LEN_MAX;
  @Value("${user.pass.len.message}")
  public String PASS_LEN_MSG;
  @Value("${user.pass.regex}")
  public String PASS_REGEX;
  @Value("${user.pass.regex.message}")
  public String PASS_REGEX_MSG;

}
