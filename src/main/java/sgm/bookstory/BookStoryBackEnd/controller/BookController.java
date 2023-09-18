package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.enums.CategoryType;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    // 책 추가하기
    public ResponseModel<Book> addBook(@RequestBody Book book){
        final Book savedBook = bookService.addBook(book);
        return new ResponseModel<>(HttpStatus.OK.value(), "Book Saved", savedBook);
    }

    @PostMapping("/addAll")
    // 책 여러개 추가하기
    public ResponseModel<List<Book>> addBooks(@RequestBody List<Book> books){
        final List<Book> savedBooks = bookService.addBooks(books);
        return new ResponseModel<>(HttpStatus.OK.value(), "Books Saved", savedBooks);
    }

    @PostMapping("/remove")
    // 책 제거하기
    public ResponseModel<Book> removeBook(@RequestBody Book book){
        final Book removedBook = bookService.removeBook(book);
        return new ResponseModel<>(HttpStatus.OK.value(), "Book Removed", removedBook);
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

    @GetMapping("get5BooksByCategoryAge/{categoryAge}")
    // 해당 카테고리에 포함된 책 최대 5권 반환 (playCount 높은 순)
    public ResponseEntity<List<Book>> getTop5BooksByCategoryAgeOrderByPlayCountDesc(@PathVariable(name="categoryAge") CategoryType categoryType){
        return ResponseEntity.ok(bookService.getTop5BooksByCategoryAgeOrderByPlayCountDesc(categoryType));
    }
}
