package shorten.shortenurlservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shorten.shortenurlservice.domain.ShortenUrl;
import shorten.shortenurlservice.domain.ShortenUrlRepository;
import shorten.shortenurlservice.domain.exception.LackOfShortenUrlKeyException;
import shorten.shortenurlservice.domain.exception.NotFoundShortenUrlException;
import shorten.shortenurlservice.presentation.dto.request.ShortenUrlCreateRequestDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 단위 테스트(목 객체를 만들어야함)
@ExtendWith(MockitoExtension.class)
class SimpleShortenUrlServiceUnitTest {
  @Mock
  private ShortenUrlRepository shortenUrlRepository;

  @InjectMocks
  private SimpleShortenUrlService simpleShortenUrlService;

  @Test
  @DisplayName("단축 URL이 계속 중복되면 LackOfShortenUrlKeyException 예외가 발생해야 한다.")
  void throwLackOfShortenUrlKeyException() {
    // given
    ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto("google.com");

    // when: Mock 객체의 수행 흐름을 적어줘야 한다.
    when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(new ShortenUrl(null, null));

    // then
    Assertions.assertThrows(LackOfShortenUrlKeyException.class, () -> {
      simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
    });
  }

  @Test
  @DisplayName("없는 키를 조회한다면 NotFoundShortenUrlException 을 throw 한다.")
  void throwNotFoundShortenUrlException() {
    // given
    String key = "random-key";

    // when: Mock 객체의 수행 흐름을 적어줘야 한다.
    when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(null);

    // then
    Assertions.assertThrows(NotFoundShortenUrlException.class, () -> {
      simpleShortenUrlService.getOriginalUrl(key);
    });
  }
}