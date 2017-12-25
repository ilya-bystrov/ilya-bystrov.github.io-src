package io.github.ilyabystrov.urlshortener.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Response {

  private final boolean success;
  private final String description;
  private final Optional<String> password;

  public Response(boolean success, String description) {
    this(success,description, Optional.empty());

  }

  public Response(boolean success, String description, Optional<String> password) {
    this.success = success;
    this.description = description;
    this.password = password;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getDescription() {
    return description;
  }

  public Optional<String> getPassword() {
    return password;
  }
}
