package sgm.bookstory.BookStoryBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public class ResponseModelException<T> extends ResponseModel<T>{
    private final HttpStatus status;
    private final String message;
}
