package sgm.bookstory.BookStoryBackEnd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgm.bookstory.BookStoryBackEnd.enums.CategoryType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="bus")
public class Book {
    @Id
    private Long id;
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
    String playTime;
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
}
