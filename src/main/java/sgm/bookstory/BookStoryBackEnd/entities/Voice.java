package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "voice")
public class Voice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID voiceId; // record를 만들 때 자동 생성됨
    @Column(nullable = false)
    private String voiceName;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VoiceStatus status;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
}
