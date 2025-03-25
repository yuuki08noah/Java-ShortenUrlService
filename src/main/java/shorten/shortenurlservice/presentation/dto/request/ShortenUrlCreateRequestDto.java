package shorten.shortenurlservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class ShortenUrlCreateRequestDto {
  @NotNull
  private String originalUrl;

  public ShortenUrlCreateRequestDto(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }
}
