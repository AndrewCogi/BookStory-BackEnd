package sgm.bookstory.BookStoryBackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sgm.bookstory.BookStoryBackEnd.enums.VoiceStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "voice")
public class Voice {
    // 자동으로 추가되는 정보들 -------------
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID voiceId;
    // ----------------------------------

    // 목소리 추가할 때 넣어야 하는 정보 ------
    @Id
    private String voiceName;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VoiceStatus status;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
    // ----------------------------------

    // 테이블 간 연관관계 설정 --------------
    @JsonIgnore
    @OneToMany(mappedBy = "voice", cascade = CascadeType.ALL)
    private List<VoiceInference> voiceInferences;
    // ----------------------------------

}
