package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.BookRepository;
import sgm.bookstory.BookStoryBackEnd.services.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        if(bookRepository.existsById(book.getBookId())) throw new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book ID already exists! - Book Title: "+book.getTitle());
        return bookRepository.save(book);
    }

    @Override
    public Book removeBook(Book book){
        if(!bookRepository.existsById(book.getBookId())) throw new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not exists!");
        bookRepository.deleteById(book.getBookId());
        return book;
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByTitleContaining(String title) {
        return bookRepository.findByTitleContaining(title).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Book not found!"));
    }
}
