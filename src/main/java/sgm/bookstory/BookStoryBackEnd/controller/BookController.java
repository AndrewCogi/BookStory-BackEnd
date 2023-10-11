package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseModel<Long> removeBook(@RequestBody Book book){
        final Long removedBookId = bookService.removeBook(book);
        return new ResponseModel<>(HttpStatus.OK.value(), "Book Removed", removedBookId);
    }

    @GetMapping("/all")
    // 모든 책 받기
    public ResponseModel<List<Book>> getAllBook(){
        final List<Book> allBooks = bookService.getAllBook();
        return new ResponseModel<>(HttpStatus.OK.value(), "All Books", allBooks);
    }

    @GetMapping("/{title}")
    // 특정 제목의 책 받기 (검색)
    public ResponseModel<List<Book>> getBookByTitleContaining(@PathVariable(name="title") String title){
        final List<Book> searchResults = bookService.getBookByTitleContaining(title);
        return new ResponseModel<>(HttpStatus.OK.value(), "Search Result", searchResults);
    }

    @GetMapping("getBooksByCategoryAge/{categoryAge}")
    // 해당 카테고리(age)에 포함된 책 최대 n(limit)권 반환 (playCount 높은 순)
    public ResponseModel<List<Book>> getBooksByCategoryAgeOrderByPlayCountDesc(
            @PathVariable(name = "categoryAge") CategoryType categoryType,
            @RequestParam(name = "limit") String limit) {
        final List<Book> searchResults = bookService.getBooksByCategoryAgeOrderByPlayCountDesc(categoryType,Integer.parseInt(limit));
        return new ResponseModel<>(HttpStatus.OK.value(), "Books By CategoryAge", searchResults);
    }

    @GetMapping("getBooksByCategoryType/{categoryType}")
    // 해당 카테고리(not age)에 포함된 책 최대 n(limit)권 반환 (playCount 높은 순)
    public ResponseModel<List<Book>> getBooksByCategoryTypeOrderByPlayCountDesc(
            @PathVariable(name = "categoryType") List<CategoryType> categoryTypes,
            @RequestParam(name = "limit") String limit) {
        final List<Book> searchResults = bookService.getBooksByCategoryTypeOrderByPlayCountDesc(categoryTypes,Integer.parseInt(limit));
        return new ResponseModel<>(HttpStatus.OK.value(), "Books By CategoryTypes", searchResults);
    }
}
