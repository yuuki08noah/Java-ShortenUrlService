package shorten.shortenurlservice.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import shorten.shortenurlservice.application.SimpleShortenUrlService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 통합 테스트 중에서도 웹 통신을 테스트 해봐야하는 Controller 테스트이다.
@WebMvcTest(ShortenUrlRestController.class)
// 이후에 등장하는 Autowired 에서 진짜를 주입시킬 지 가짜를 주입시킬 지 헷갈린다면 이것에 기반해서 주입해주도록 함
@Import(ShortenUrlRestControllerTest.MockServiceConfig.class)
class ShortenUrlRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SimpleShortenUrlService simpleShortenUrlService;

  @Test
  @DisplayName("원래의 URL로 리다이렉트 시켜야한다.")
  void redirectTest() throws Exception {
    // given
    String expectedOriginalUrl = "http://www.google.com";

    // when
    when(simpleShortenUrlService
            .getOriginalUrl(any()))
            .thenReturn(expectedOriginalUrl);

    // then
    mockMvc.perform(get("/any-key"))
            .andExpect(status().isMovedPermanently())
            .andExpect(header().string("Location", expectedOriginalUrl));
  }

  // Spring 을 통해서 위에서 Autowired 를 실제 객체로 시켰었는데
  // 불러오는 것을 스프링 컨테이너에 가짜 객체도 함께 등록함
  static class MockServiceConfig {
    @Bean
    public SimpleShortenUrlService simpleShortenUrlService() {
      return mock(SimpleShortenUrlService.class);
    }
  }
}