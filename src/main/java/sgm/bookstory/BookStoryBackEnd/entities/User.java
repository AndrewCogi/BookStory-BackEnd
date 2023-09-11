package sgm.bookstory.BookStoryBackEnd.entities;

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
    @Id
    private String userEmail;
    @Column
    private String password;
    @Column
    private UserStatus userStatus;
    @Column
    private Timestamp lastLoginTime;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<View> views;
}
