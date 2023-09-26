package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.FavoriteService;
import sgm.bookstory.BookStoryBackEnd.services.UserService;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseModel<Favorite> addFavorite(@RequestBody Favorite favorite){
        final Favorite savedFavorite = favoriteService.addFavorite(favorite);
        favoriteService.updateBookInfo_MANUAL(savedFavorite.getBook());
        return new ResponseModel<>(HttpStatus.OK.value(), "Favorite Saved", savedFavorite);
    }

    @PostMapping("/remove")
    public ResponseModel<Favorite> removeFavorite(@RequestBody Favorite favorite){
        final Favorite removedFavorite = favoriteService.removeFavorite(favorite);
        favoriteService.updateBookInfo_MANUAL(removedFavorite.getBook());
        return new ResponseModel<>(HttpStatus.OK.value(), "Favorite Removed", removedFavorite);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByUser_UserEmail(
            @PathVariable(name = "userEmail") String userEmail,
            @RequestHeader(name = "Authorization") String authHeader) {
        if(userService.isValidUser(userEmail, authHeader)){
            // userEmail에 대한 모든 favorite 값 반환
            return ResponseEntity.ok(favoriteService.getAllFavoriteByUser_UserEmail(userEmail));
        }
        List<Favorite> emptyList = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyList);
    }

    @GetMapping("/{userEmail}/{bookId}")
    public ResponseModel<Boolean> existsFavorite(
            @PathVariable(name = "userEmail") String userEmail,
            @PathVariable(name = "bookId") String bookId,
            @RequestHeader(name = "Authorization") String authHeader){
        if(userService.isValidUser(userEmail, authHeader)){
            // userEmail과 bookId에 대한 favorite 존재여부 반환
            return new ResponseModel<>(HttpStatus.OK.value(), "Favorite find result",favoriteService.existsFavorite(userEmail, Long.parseLong(bookId)));
        }
        return new ResponseModel<>(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized", null);
    }
}
