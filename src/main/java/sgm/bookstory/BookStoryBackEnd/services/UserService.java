package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.User;

public interface UserService {
    User addUser(User user);
    User removeUser(User user);
    User userLogin(User user);
    User userLogout(User user);
    boolean isValidUser(String userEmail, String authHeader);
}
