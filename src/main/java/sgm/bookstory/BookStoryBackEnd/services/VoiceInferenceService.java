package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.entities.VoiceInference;

import java.util.List;

public interface VoiceInferenceService {
    VoiceInference addVoiceInference(VoiceInference voiceInference);
    VoiceInference removeVoiceInference(VoiceInference voiceInference);
    List<VoiceInference> getAllVoiceInferenceByUser_UserEmailAndBook_BookId(String userEmail, Long bookId);
}
