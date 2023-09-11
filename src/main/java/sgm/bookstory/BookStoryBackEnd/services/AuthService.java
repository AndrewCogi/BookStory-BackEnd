package sgm.bookstory.BookStoryBackEnd.services;

import sgm.bookstory.BookStoryBackEnd.entities.User;

public interface AuthService {
    User addUser(User user);
    User removeUser(User user);
    User userLogin(User user);
    User userLogout(User user);
}
