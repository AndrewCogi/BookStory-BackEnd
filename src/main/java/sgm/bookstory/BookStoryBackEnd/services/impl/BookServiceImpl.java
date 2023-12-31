package sgm.bookstory.BookStoryBackEnd.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.enums.CategoryType;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.BookRepository;
import sgm.bookstory.BookStoryBackEnd.repos.FavoriteRepository;
import sgm.bookstory.BookStoryBackEnd.services.BookService;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ViewService viewService;
    @Autowired
    private FavoriteRepository favoriteService;

    @Override
    public Book addBook(Book book) {
        // 추가할 책 존재여부 확인
        if(bookRepository.existsById(book.getBookId()))
            throw new BookStoryApiException(
                    HttpStatus.BAD_REQUEST,
                    "Book ID("+book.getBookId()+") already exists! - Book Title: "+bookRepository.getReferenceById(book.getBookId()).getTitle()
            );
        // 책 추가 후 추가한 책 정보 반환
        return bookRepository.save(book);
    }

    @Override
    public List<Book> addBooks(List<Book> books){
        List<Book> savedBooks = new ArrayList<>();
        // 모든 책에 대해 하나씩 진행
        for(Book book : books){
            if(bookRepository.existsById(book.getBookId()))
                throw new BookStoryApiException(
                        HttpStatus.BAD_REQUEST,
                        "Book ID("+book.getBookId()+") already exists! - Book Title: "+bookRepository.getReferenceById(book.getBookId()).getTitle()
                );
            // 책 추가
            savedBooks.add(book);
        }
        return bookRepository.saveAll(savedBooks);
    }

    @Override
    public Long removeBook(Book book){
        // 지울 책 정보 있나 확인
        bookRepository.findById(book.getBookId()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not exists!"));

        // 책 삭제
        bookRepository.deleteById(book.getBookId());
        // 지워진 책 id 반환
        return book.getBookId();
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByTitleContaining(String title) {
        System.out.println(bookRepository.findByTitleContaining(title));
        return bookRepository.findByTitleContaining(title).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not found!"));
    }

    @Override
    public List<Book> getBooksByCategoryAgeOrderByPlayCountDesc(CategoryType categoryType, int limit) {
        Pageable pageable = PageRequest.of(0, limit); // n(limit)개 항목
        return bookRepository.findBooksByCategoryAgeOrderByPlayCountDesc(categoryType, pageable).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not found!"));
    }

    @Override
    public List<Book> getBooksByCategoryTypeOrderByPlayCountDesc(List<CategoryType> categoryTypes, int limit) {
        Pageable pageable = PageRequest.of(0, limit); // n(limit)개 항목
        return bookRepository.findBooksByCategoryTypeInOrderByPlayCountDesc(categoryTypes, pageable).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not found!"));
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void updateBookInfo_AUTO() {
        System.out.println("AUTO UPDATE - Book Info");
        // 책 정보 최신화
        List<Book> books = bookRepository.findAll();
        for(Book b : books){
            b.setPlayCount(viewService.countByBookId(b.getBookId()));
            b.setFavoriteCount(favoriteService.countByBookId(b.getBookId()));
        }
    }
}
