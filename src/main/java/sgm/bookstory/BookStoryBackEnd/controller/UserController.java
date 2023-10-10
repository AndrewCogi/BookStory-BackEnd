package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseModel<User> removeUser(@RequestBody User user, @RequestHeader("Authorization") String authHeader){
        int statusCode = userService.isValidUser(user.getUserEmail(), authHeader).getStatusCode();
        if(statusCode == 200){
            final User removedUser = userService.removeUser(user);
            return new ResponseModel<>(HttpStatus.OK.value(), "User Removed", removedUser);
        }
        else if(statusCode == 403){
            // 토큰 만료를 알림
            List<Favorite> emptyList = new ArrayList<>();
            return new ResponseModel<>(HttpStatus.FORBIDDEN.value(), "Token Expired", null);
        }
        else{
            // 로그인하지 않은 사용자
            return new ResponseModel<>(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized", null);
        }
    }
}
