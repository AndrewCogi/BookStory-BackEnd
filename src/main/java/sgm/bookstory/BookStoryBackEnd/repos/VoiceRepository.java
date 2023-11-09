package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoiceRepository extends JpaRepository<Voice, UUID> {
    boolean existsByVoiceName(String voiceName);
    Optional<Voice> findByVoiceName(String voiceName);
    Optional<List<Voice>> findAllByUser_UserEmail(String userEmail);
}
