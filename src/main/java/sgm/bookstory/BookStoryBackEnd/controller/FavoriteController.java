package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.FavoriteService;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @PostMapping("/add")
    public ResponseModel<Favorite> addFavorite(@RequestBody Favorite favorite){
        final Favorite savedFavorite = favoriteService.addFavorite(favorite);
        return new ResponseModel<>(HttpStatus.OK.value(), "Favorite Saved", savedFavorite);
    }

    @PostMapping("/remove")
    public ResponseModel<Favorite> removeFavorite(@RequestBody Favorite favorite){
        final Favorite removedFavorite = favoriteService.removeFavorite(favorite);
        return new ResponseModel<>(HttpStatus.OK.value(), "Favorite Removed", removedFavorite);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByUser_UserEmail(@PathVariable(name = "userEmail") String userEmail){
        return ResponseEntity.ok(favoriteService.getAllFavoriteByUser_UserEmail(userEmail));
    }
}
