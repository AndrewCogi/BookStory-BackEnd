package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.entities.VoiceInference;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceInferenceStatus;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceStatus;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.VoiceInferenceRepository;
import sgm.bookstory.BookStoryBackEnd.repos.VoiceRepository;
import sgm.bookstory.BookStoryBackEnd.services.VoiceInferenceService;
import sgm.bookstory.BookStoryBackEnd.services.VoiceService;

import java.util.List;

@Service
public class VoiceInferenceServiceImpl implements VoiceInferenceService {
    @Autowired
    private VoiceInferenceRepository voiceInferenceRepository;
    @Override
    public VoiceInference addVoiceInference(VoiceInference voiceInference) {
        // 목소리 추론 중복 확인
        if(voiceInferenceRepository.existsByVoice_VoiceNameAndUser_UserEmailAndBook_BookId(
                voiceInference.getVoice().getVoiceName(),
                voiceInference.getUser().getUserEmail(),
                voiceInference.getBook().getBookId()
        )) {
            throw new BookStoryApiException(
                    HttpStatus.BAD_REQUEST,
                    "Voice Inference " +
                    "(VoiceName: "+voiceInference.getVoice().getVoiceName()+") " +
                    "(UserEmail: "+voiceInference.getUser().getUserEmail()+") " +
                    "(BookId: "+voiceInference.getBook().getBookId()+") " +
                    "already exists!");
        }
        // 목소리 추론 상태 기본값 설정
        voiceInference.setVoiceInferenceStatus(VoiceInferenceStatus.init);
        // 목소리 추가한 후 추가한 목소리 정보 반환
        return voiceInferenceRepository.save(voiceInference);
    }
    @Override
    public VoiceInference removeVoiceInference(VoiceInference voiceInference) {
        // 지울 voiceInference 정보 찾기
        VoiceInference removedVoiceInference = voiceInferenceRepository.findByVoice_VoiceNameAndUser_UserEmailAndBook_BookId(
                voiceInference.getVoice().getVoiceName(),
                voiceInference.getUser().getUserEmail(),
                voiceInference.getBook().getBookId()
        ).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Voice Inference not exists!"));
        // voice 삭제
        voiceInferenceRepository.deleteById(removedVoiceInference.getVoiceInferenceId());
        // 지워진 favorite 정보 반환
        return removedVoiceInference;
    }

    @Override
    public List<VoiceInference> getAllVoiceInferenceByUser_UserEmailAndBook_BookId(String userEmail, Long bookId) {
        return voiceInferenceRepository.findAllByUser_UserEmailAndBook_BookId(userEmail, bookId);
    }
}
