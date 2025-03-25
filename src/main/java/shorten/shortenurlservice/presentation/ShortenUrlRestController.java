package shorten.shortenurlservice.presentation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shorten.shortenurlservice.application.SimpleShortenUrlService;
import shorten.shortenurlservice.presentation.dto.request.ShortenUrlCreateRequestDto;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlInformationDto;
import shorten.shortenurlservice.presentation.dto.response.ShortenUrlCreateResponseDto;

import java.net.URI;

@RestController
public class ShortenUrlRestController {
  private final SimpleShortenUrlService simpleShortenUrlService;

  @Autowired
  public ShortenUrlRestController(SimpleShortenUrlService shortenUrlService) {
    this.simpleShortenUrlService = shortenUrlService;
  }
  @PostMapping(value="/shortenUrl")
  public ResponseEntity<ShortenUrlCreateResponseDto> shortenUrl(
          @Valid
          @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {
    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
    return ResponseEntity.ok(shortenUrlCreateResponseDto);
  }

  @GetMapping(value = "/{shortenUrlKey}")
  public ResponseEntity<?> redirectShortenUrl(
          @PathVariable("shortenUrlKey") String shortenUrlKey
  ) {
    String originalUrl = simpleShortenUrlService.getOriginalUrl(shortenUrlKey);
    URI redirectURI = URI.create(originalUrl);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(redirectURI);
    return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
  }

  @GetMapping(value = "/shortenUrl/{shortenUrlKey}")
  public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(
          @PathVariable("shortenUrlKey") String shortenUrlKey) {
    ShortenUrlInformationDto shortenUrlInformationDto =
            simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);
    return ResponseEntity.ok(shortenUrlInformationDto);
  }
}
