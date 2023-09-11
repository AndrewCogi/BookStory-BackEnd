package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "view")
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;
    @Column
    @CreationTimestamp
    Timestamp showTime;
}
