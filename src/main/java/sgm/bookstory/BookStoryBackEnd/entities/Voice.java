package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @Column
    @Builder.Default()
    private int position = -1;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
}
