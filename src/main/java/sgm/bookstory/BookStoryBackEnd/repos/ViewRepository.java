package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.View;

public interface ViewRepository extends JpaRepository<View, Long> {
}
