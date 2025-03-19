package shorten.shortenurlservice.infrastructure;

import org.springframework.stereotype.Repository;
import shorten.shortenurlservice.domain.ShortenUrl;
import shorten.shortenurlservice.domain.ShortenUrlRepository;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlInformationDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository {
  private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

  @Override
  public void saveShortenUrl(ShortenUrl shortenUrl) {
    shortenUrls.put(shortenUrl.getShortenedUrlKey(), shortenUrl);
  }

  @Override
  public ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey) {
    ShortenUrl shortenUrl = shortenUrls.get(shortenUrlKey);
    return shortenUrl;
  }
}
