package shorten.shortenurlservice.domain;

import java.util.Random;

public class ShortenUrl {
  private String originalUrl;
  private String shortenedUrlKey;
  private Long redirectCount;

  public ShortenUrl(String originalUrl, String shortenedUrlKey) {
    this.originalUrl = originalUrl;
    this.shortenedUrlKey = shortenedUrlKey;
    this.redirectCount = 0L;
  }

  public static String generateShortenedUrl() {
    String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    Random random = new Random();
    StringBuilder shortenedUrl = new StringBuilder();
    for(int i = 0; i < 8; i++) {
      char base56Character = base56Characters.charAt(random.nextInt(base56Characters.length()));
      shortenedUrl.append(base56Character);
    }
    return shortenedUrl.toString();
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public String getShortenedUrlKey() {
    return shortenedUrlKey;
  }

  public Long getRedirectCount() {
    return redirectCount;
  }

  public void increaseRedirectCount() {
    this.redirectCount++;
  }
}