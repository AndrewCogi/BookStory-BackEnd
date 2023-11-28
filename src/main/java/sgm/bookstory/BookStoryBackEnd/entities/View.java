package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "view")
public class View {
    // 자동으로 추가되는 정보들 -------------
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID viewId;
    @Column
    @CreationTimestamp
    private Timestamp showTime;
    // ----------------------------------

    // 뷰 추가할 때 넣어야 하는 정보 ---------
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // bookId 값은 필수
    // ----------------------------------
}
