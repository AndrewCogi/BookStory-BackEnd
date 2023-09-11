package sgm.bookstory.BookStoryBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BookStoryApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}