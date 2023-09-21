package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite addFavorite(Favorite favorite);
    Favorite removeFavorite(Favorite favorite);
    boolean existsFavorite(String userEmail, Long bookId);
    List<Favorite> getAllFavoriteByUser_UserEmail(String userEmail);
}
