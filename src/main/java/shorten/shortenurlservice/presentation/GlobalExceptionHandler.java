package shorten.shortenurlservice.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shorten.shortenurlservice.domain.exception.LackOfShortenUrlKeyException;
import shorten.shortenurlservice.domain.exception.NotFoundShortenUrlException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundShortenUrlException.class)
  public ResponseEntity<?> handleNotFoundShortenUrlException(
          NotFoundShortenUrlException e) {
    return new ResponseEntity<>("Invalid Shorten URL", HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(LackOfShortenUrlKeyException.class)
  public ResponseEntity<?> handleLackOfShortenUrlException(
          NotFoundShortenUrlException e) {
    return new ResponseEntity<>("Lack of Shorten URL", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}