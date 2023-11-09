package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.enums.UserStatus;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceStatus;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.VoiceRepository;
import sgm.bookstory.BookStoryBackEnd.services.VoiceService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class VoiceServiceImpl implements VoiceService {
    @Autowired
    private VoiceRepository voiceRepository;
    @Override
    public Voice addVoice(Voice voice) {
        // 목소리 이름 중복 확인
        System.out.println("voiceID: "+voice.getVoiceName());
        if(voiceRepository.existsByVoiceName(voice.getVoiceName()))
            throw new BookStoryApiException(
                    HttpStatus.BAD_REQUEST,
                    "Voice Name("+voice.getVoiceName()+") already exists!");
        // 목소리의 상태 기본값 설정
        voice.setStatus(VoiceStatus.init);
        // 목소리 추가한 후 추가한 목소리 정보 반환
        return voiceRepository.save(voice);
    }
    @Override
    public Voice removeVoice(Voice voice) {
        // 지울 voice 정보 찾기
        Voice removedVoice = voiceRepository.findByVoiceName(voice.getVoiceName()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Voice not exists!"));
        // voice 삭제
        voiceRepository.deleteById(removedVoice.getVoiceId());
        // 지워진 favorite 정보 반환
        return removedVoice;
    }
    @Override
    public List<Voice> getAllVoiceByUser_UserEmail(String userEmail){
        return voiceRepository.findAllByUser_UserEmail(userEmail).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Voice not found!"));
    }
}
