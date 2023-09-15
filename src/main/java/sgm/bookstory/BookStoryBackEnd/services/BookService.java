package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> addBooks(List<Book> books);
    Book removeBook(Book book);
    List<Book> getAllBook();
    List<Book> getBookByTitleContaining(String title);
}