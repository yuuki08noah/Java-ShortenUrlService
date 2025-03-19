package shorten.shortenurlservice.domain;

import org.springframework.stereotype.Repository;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlInformationDto;

@Repository
public interface ShortenUrlRepository {
  void saveShortenUrl(ShortenUrl shortenUrl);

  ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey);
}