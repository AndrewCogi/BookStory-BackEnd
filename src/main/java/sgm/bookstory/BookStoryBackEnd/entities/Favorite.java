package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID favoriteId; // favorite를 만들 때 자동 생성됨
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user; // userEmail 값은 필수
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; // bookId 값은 필수
}
