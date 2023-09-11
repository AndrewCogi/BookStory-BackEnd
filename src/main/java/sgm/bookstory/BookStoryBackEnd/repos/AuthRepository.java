package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.User;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String email);
}
