package shorten.shortenurlservice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shorten.shortenurlservice.domain.ShortenUrl;
import shorten.shortenurlservice.domain.ShortenUrlRepository;
import shorten.shortenurlservice.domain.exception.LackOfShortenUrlKeyException;
import shorten.shortenurlservice.domain.exception.NotFoundShortenUrlException;
import shorten.shortenurlservice.presentation.dto.request.ShortenUrlCreateRequestDto;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlCreateResponseDto;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlInformationDto;

@Service
public class SimpleShortenUrlService {
  private final ShortenUrlRepository shortenUrlRepository;

  @Autowired
  public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
    this.shortenUrlRepository = shortenUrlRepository;
  }

  public ShortenUrlCreateResponseDto generateShortenUrl(
          ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
  ) {
    String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
    // generate shorten url from original url
    String shortenedUrlKey = repeatIfShortenKeyExists();

    // create shorten url domain object
    ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenedUrlKey);

    // save shorten url to repository
    shortenUrlRepository.saveShortenUrl(shortenUrl);

    // map shorten url to response dto and return it
    return new ShortenUrlCreateResponseDto(shortenUrl);
  }

  public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    if(shortenUrl == null) {
      throw new NotFoundShortenUrlException();
    }
    ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
    return shortenUrlInformationDto;
  }

  public String getOriginalUrl(String shortenUrlKey) {
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    if(shortenUrl == null) {
      throw new NotFoundShortenUrlException();
    }
    shortenUrl.increaseRedirectCount();
    shortenUrlRepository.saveShortenUrl(shortenUrl);
    return shortenUrl.getOriginalUrl();
  }

  private String repeatIfShortenKeyExists() throws LackOfShortenUrlKeyException {
    String shortenUrlKey = ShortenUrl.generateShortenedUrl();
    final int MAX_RETRY_COUNT = 5;
    int count = 0;
    while (count++ < MAX_RETRY_COUNT && shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey) != null) {
      shortenUrlKey = ShortenUrl.generateShortenedUrl();
    }
    if (shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey) != null) {
      throw new LackOfShortenUrlKeyException();
    }
    return shortenUrlKey;
  }
}
