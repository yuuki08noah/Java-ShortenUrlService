package shorten.shortenurlservice.presentation.dto.response;

import shorten.shortenurlservice.domain.ShortenUrl;

public class ShortenUrlInformationDto {
  private String originalUrl;
  private String shortenedUrlKey;
  private Long redirectCount;

  public String getOriginalUrl() {
    return originalUrl;
  }

  public String getShortenedUrlKey() {
    return shortenedUrlKey;
  }

  public Long getRedirectCount() {
    return redirectCount;
  }

  public ShortenUrlInformationDto(ShortenUrl shortenUrl) {
    this.originalUrl = shortenUrl.getOriginalUrl();
    this.shortenedUrlKey = shortenUrl.getShortenedUrlKey();
    this.redirectCount = shortenUrl.getRedirectCount();
  }
}
