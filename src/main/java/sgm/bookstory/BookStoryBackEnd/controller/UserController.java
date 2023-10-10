package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseModel<User> loginUser(@RequestBody User user){
        final User loginUser = userService.userLogin(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Login Complete", loginUser);
    }
    @PostMapping("/logout")
    public ResponseModel<User> logoutUser(@RequestBody User user){
        final User logoutUser = userService.userLogout(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Logout Complete", logoutUser);
    }
    @PostMapping("/add")
    public ResponseModel<User> addUser(@RequestBody User user){
        final User savedUser = userService.addUser(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Added", savedUser);
    }
    @PostMapping("/remove")
    public ResponseModel<User> removeUser(@RequestBody User user){
        final User removedUser = userService.removeUser(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Removed", removedUser);
    }

    @GetMapping("/tokenValid")
    public ResponseEntity<Boolean> getIsTokenValid(
            @RequestParam(name = "userEmail") String userEmail,
            @RequestHeader(name = "authorization") String authHeader){
        int statusCode = userService.isValidUser(userEmail, authHeader).getStatusCode();
        if(statusCode == 200){ // 살아있는 유저. true 반환.
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
