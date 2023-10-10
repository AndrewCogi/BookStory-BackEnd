package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;

import java.sql.Timestamp;
import java.util.Map;

public interface UserService {
    User addUser(User user);
    User removeUser(User user);
    User userLogin(User user);
    User userLogout(User user);
    ResponseModel<Boolean> isValidUser(String userEmail, String authHeader);
    // isValidUser 설명 : 200 (정상유저), 403 (토큰만료유저), 401 (게스트유저)
    Timestamp getLastStatusUpdateTime(String userEmail);
}
