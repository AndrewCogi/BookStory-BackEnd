package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceInferenceStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "voice_inference")
public class VoiceInference {
    // 자동으로 추가되는 정보들 -------------
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID voiceInferenceId;
    @Column(nullable = false) // VoiceInferenceServiceImpl 에서 추가됨
    @Enumerated(value = EnumType.STRING)
    private VoiceInferenceStatus voiceInferenceStatus;
    // ----------------------------------

    // 추론정보 추가할 때 넣어야 하는 정보 ----
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
    @ManyToOne
    @JoinColumn(name = "voice_name")
    private Voice voice; // voiceName 값은 필수
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // bookId 값은 필수
    // ----------------------------------
}
