package shorten.shortenurlservice.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shorten.shortenurlservice.presentation.dto.request.ShortenUrlCreateRequestDto;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlCreateResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 통합테스트를 진행할 때는 스프링의 힘을 빌려야하기 때문에 아래 어노테이션을 넣어줘야한다.
@SpringBootTest
class SimpleShortenUrlServiceTest {

  // Spring Container 로부터 실제 서비스 객체를 불러와서 연결시키는 과정
  @Autowired
  private SimpleShortenUrlService simpleShortenUrlService;

  @Test
  @DisplayName("URL을 단축한 후에 단축된 URL 키로 조회하면 원래 URL이 조회되어야한다.")
  void shortenUrlAddTest() {
    // given(전제: ~가 주어졌을 때)
    String expectedOriginalUrl = "http://www.google.com";
    ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);

    // when(조건: ~를 했을 때)
    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
    String shortendUrlKey = shortenUrlCreateResponseDto.getShortendUrlKey();
    String originalUrl = simpleShortenUrlService.getOriginalUrl(shortendUrlKey);

    // then(결과: 기대하는 결과가 잘 나왔는지)
    assertEquals(expectedOriginalUrl, originalUrl);
    assertThat(originalUrl).isEqualTo(expectedOriginalUrl);
    assertTrue(originalUrl.equals(expectedOriginalUrl));
  }
}