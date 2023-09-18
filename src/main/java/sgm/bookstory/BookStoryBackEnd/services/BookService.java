package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.enums.CategoryType;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> addBooks(List<Book> books);
    Book removeBook(Book book);
    List<Book> getAllBook();
    List<Book> getBookByTitleContaining(String title);
    List<Book> getTop5BooksByCategoryAgeOrderByPlayCountDesc(CategoryType categoryType);
}