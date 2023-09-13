package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<List<Book>> findByTitleContaining(String title); // 책 제목에 'title' 이 포함되는 책 정보들 반환
    Optional<Book> findById(Long bookId); // 책 id를 통해 책 정보를 반환
}
