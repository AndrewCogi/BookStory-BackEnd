package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name="book")
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
    private String playTime;
    @Column(nullable = false)
    private Long favorite;
    @Column(nullable = false)
    private Long playCount;
    @Column(nullable = false)
    private Double rate;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private String description;
    @Column
    @CreationTimestamp
    private Timestamp updateTime;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<View> views;
}
