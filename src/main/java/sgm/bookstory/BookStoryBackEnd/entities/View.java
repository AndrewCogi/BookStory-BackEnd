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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID viewId; // record를 만들 때 자동 생성됨
    @Column
    @CreationTimestamp
    private Timestamp showTime; // record를 만들 때 자동 생성됨
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // bookId 값은 필수
}
