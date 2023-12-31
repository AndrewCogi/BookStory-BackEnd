package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sgm.bookstory.BookStoryBackEnd.entities.View;

import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, UUID> {
    @Query("SELECT COUNT(v) FROM View v WHERE v.book.bookId = :bookId")
    Long countByBookId(@Param("bookId") Long bookId);
}
