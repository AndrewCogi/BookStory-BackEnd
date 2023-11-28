package sgm.bookstory.BookStoryBackEnd.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.entities.VoiceInference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoiceInferenceRepository extends JpaRepository<VoiceInference, UUID> {
    boolean existsByVoice_VoiceNameAndUser_UserEmailAndBook_BookId(String voiceName, String userEmail, Long bookId);
    Optional<VoiceInference> findByVoice_VoiceNameAndUser_UserEmailAndBook_BookId(String voiceName, String userEmail, Long bookId);
//    Optional<Voice> findByVoiceName(String voiceName);
//    Optional<List<Voice>> findAllByUser_UserEmail(String userEmail);
}
