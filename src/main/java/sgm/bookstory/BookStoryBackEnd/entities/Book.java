package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import sgm.bookstory.BookStoryBackEnd.enums.CategoryType;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="book")
public class Book {
    @Id
    private Long bookId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String drawer;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private Long bookPage;
    @Column(nullable = false)
    private CategoryType categoryAge;
    @Column(nullable = false)
    private List<CategoryType> categoryType;
    @Column(nullable = false)
    Long playTime;
    @Column(nullable = false)
    Long favorite;
    @Column(nullable = false)
    Long playCount;
    @Column(nullable = false)
    Double rate;
    @Column(nullable = false)
    String imagePath;
    @Column(nullable = false)
    String description;
    @Column
    @CreationTimestamp
    Timestamp updateTime;
}
