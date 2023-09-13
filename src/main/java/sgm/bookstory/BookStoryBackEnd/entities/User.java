package sgm.bookstory.BookStoryBackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgm.bookstory.BookStoryBackEnd.enums.UserStatus;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    // 유저 추가할 때 넣어야 하는 정보 -------
    @Id
    private String userEmail;
    // ----------------------------------

    // 자동으로 추가되는 정보들 -------------
    // UserServiceImpl 에서 추가됨
    @Column(nullable = false)
    private UserStatus userStatus;
    @Column(nullable = false)
    private Timestamp lastStatusUpdateTime;
    // ----------------------------------

    // 테이블 간 연관관계 설정 --------------
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<View> views;
    // ----------------------------------
}
