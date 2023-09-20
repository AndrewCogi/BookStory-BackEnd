package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    boolean existsByUser_UserEmailAndBook_BookId(String userEmail,Long bookId);
    Optional<Favorite> findByUser_UserEmailAndBook_BookId(String userEmail,Long bookId);
    Optional<List<Favorite>> findAllByUser_UserEmail(String userEmail);
}
