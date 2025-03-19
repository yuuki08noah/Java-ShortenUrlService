package shorten.shortenurlservice.presentation.dto.response;

import shorten.shortenurlservice.domain.ShortenUrl;

public class ShortenUrlCreateResponseDto {
  private String originalUrl;
  private String shortendUrlKey;

  public String getOriginalUrl() {
    return originalUrl;
  }

  public String getShortendUrlKey() {
    return shortendUrlKey;
  }

  public ShortenUrlCreateResponseDto(ShortenUrl shortenUrl) {
    this.originalUrl = shortenUrl.getOriginalUrl();
    this.shortendUrlKey = shortenUrl.getShortenedUrlKey();
  }
}
