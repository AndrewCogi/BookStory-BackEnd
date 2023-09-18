package sgm.bookstory.BookStoryBackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    // 책 추가할 때 넣어야 하는 정보들 -------
    @Id
    // 책의 고유 ID
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
    @Enumerated(value = EnumType.STRING)
    private CategoryType categoryAge;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private List<CategoryType> categoryType;
    @Column(nullable = false)
    private Long playTime;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp creationTime;
    // ----------------------------------

    // 자동으로 추가되는 정보들 -------------
    // BookServiceImpl 에서 추가됨
    @Column(nullable = false)
    private Long playCount;
    // ----------------------------------

    // 테이블 간 연관관계 설정 --------------
    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<View> views;
    // ----------------------------------
}
