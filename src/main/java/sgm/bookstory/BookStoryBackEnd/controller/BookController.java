package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Value("${adminPassword}")
    // addBook()을 위한 비밀번호. 비밀번호는 application.properties에 정의
    private String adminPassword;

    @PostMapping("/add")
    // 책 추가하기
    public ResponseModel<Book> addBook(@RequestBody Book book, @RequestParam String password){
        if(!adminPassword.equals(password)){
            throw new BookStoryApiException(HttpStatus.UNAUTHORIZED, "Incorrect admin password.");
        }
        final Book savedBook = bookService.addBook(book);
        return new ResponseModel<>(HttpStatus.OK.value(), "Book Saved", savedBook);
    }

    @GetMapping("/all")
    // 모든 책 받기
    public ResponseEntity<List<Book>> getAllBook(){
        return ResponseEntity.ok(bookService.getAllBook());
    }

    @GetMapping("/{title}")
    // 특정 제목의 책 받기 (검색)
    public ResponseEntity<List<Book>> getBookByTitleContaining(@PathVariable(name="title") String title){
        return ResponseEntity.ok(bookService.getBookByTitleContaining(title));
    }
}
