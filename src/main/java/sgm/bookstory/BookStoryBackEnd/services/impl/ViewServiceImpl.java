package sgm.bookstory.BookStoryBackEnd.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.repos.BookRepository;
import sgm.bookstory.BookStoryBackEnd.repos.ViewRepository;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public View addView(View view) {
        // view 정보를 저장 후 저장된 결과 반환
        return viewRepository.save(view);
    }
    @Override
    public Long countByBookId(Long bookId){
        // bookId에 해당하는 record의 개수 반환
        return viewRepository.countByBookId(bookId);
    }

    @Override
    @Transactional
    public void updateBookInfo_MANUAL(Book book) {
        System.out.println("MANUAL UPDATE - Book Info (View)");
        // 책 정보 최신화 - playCount
        Book findBook = bookRepository.findById(book.getBookId()).orElseThrow();
        findBook.setPlayCount(countByBookId(findBook.getBookId()));
    }
}