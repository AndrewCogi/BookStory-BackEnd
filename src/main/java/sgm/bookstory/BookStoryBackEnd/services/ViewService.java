package sgm.bookstory.BookStoryBackEnd.services;

import jakarta.transaction.Transactional;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.entities.View;

public interface ViewService {
    View addView(View view);
    Long countByBookId(Long bookId); // bookId에 해당하는 record의 개수 반환
    @Transactional
    void updateBookInfo_MANUAL(Book book);
}
