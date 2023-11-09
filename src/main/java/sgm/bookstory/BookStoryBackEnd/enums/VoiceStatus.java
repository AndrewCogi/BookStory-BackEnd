package sgm.bookstory.BookStoryBackEnd.enums;

public enum VoiceStatus {
    init,       // 생성됨
    recordDone, // 녹음 완료
    training,   // 훈련중
    complete,   // 완료(성공)
    none,       // 할당안됨
}
