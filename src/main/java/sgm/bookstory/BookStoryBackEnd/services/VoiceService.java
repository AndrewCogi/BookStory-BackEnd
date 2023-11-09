package sgm.bookstory.BookStoryBackEnd.services;

import jakarta.transaction.Transactional;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;

import java.util.List;

public interface VoiceService {
    Voice addVoice(Voice voice);
    Voice removeVoice(Voice voice);
    List<Voice> getAllVoiceByUser_UserEmail(String userEmail);
    Voice updateVoiceStatus(Voice voice);
}
