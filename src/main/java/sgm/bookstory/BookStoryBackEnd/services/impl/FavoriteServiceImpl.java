package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.FavoriteRepository;
import sgm.bookstory.BookStoryBackEnd.services.FavoriteService;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
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
    public List<Favorite> getAllFavoriteByUser_UserEmail(String userEmail){
        return favoriteRepository.findAllByUser_UserEmail(userEmail).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "Favorite not found!"));
    }
}
