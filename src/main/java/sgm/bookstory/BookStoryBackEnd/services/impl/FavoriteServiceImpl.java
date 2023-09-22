package sgm.bookstory.BookStoryBackEnd.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.BookRepository;
import sgm.bookstory.BookStoryBackEnd.repos.FavoriteRepository;
import sgm.bookstory.BookStoryBackEnd.services.FavoriteService;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Favorite addFavorite(Favorite favorite) {
        // 추가할 Favorite 존재여부 확인 (userEmail & bookId)
        if(favoriteRepository.existsByUser_UserEmailAndBook_BookId(favorite.getUser().getUserEmail(),favorite.getBook().getBookId())){
            throw new BookStoryApiException(
                    HttpStatus.BAD_REQUEST,
                    "You Already add this book in favorite"
            );
        }
        return favoriteRepository.save(favorite);
    }
    @Override
    public Favorite removeFavorite(Favorite favorite) {
        // 지울 favorite 정보 찾기
        Favorite removedFavorite = favoriteRepository.findByUser_UserEmailAndBook_BookId(favorite.getUser().getUserEmail(),favorite.getBook().getBookId()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Favorite not exists!"));
        // favorite 삭제
        favoriteRepository.deleteById(removedFavorite.getFavoriteId());
        // 지워진 favorite 정보 반환
        return removedFavorite;
    }
    @Override
    public boolean existsFavorite(String userEmail, Long bookId) {
        return favoriteRepository.existsByUser_UserEmailAndBook_BookId(userEmail,bookId);
    }

    @Override
    public List<Favorite> getAllFavoriteByUser_UserEmail(String userEmail){
        return favoriteRepository.findAllByUser_UserEmail(userEmail).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Favorite not found!"));
    }

    @Override
    public Long countByBookId(Long bookId){
        // bookId에 해당하는 record의 개수 반환
        return favoriteRepository.countByBookId(bookId);
    }

    @Override
    @Transactional
    public void updateBookInfo_MANUAL(Book book) {
        System.out.println("MANUAL UPDATE - Book Info (Favorite, BookId: "+book.getBookId()+")");
        // 책 정보 최신화 - favorite
        Book findBook = bookRepository.findById(book.getBookId()).orElseThrow();
        findBook.setFavoriteCount(countByBookId(findBook.getBookId()));
    }
}
