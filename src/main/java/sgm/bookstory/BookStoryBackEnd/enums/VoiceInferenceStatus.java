package sgm.bookstory.BookStoryBackEnd.enums;

public enum VoiceInferenceStatus {
    init,       // 생성됨
    processing, // 추론중
    failed,     // 추론 실패
    complete    // 추론 성공
}
