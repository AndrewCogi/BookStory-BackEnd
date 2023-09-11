package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.enums.UserStatus;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.AuthRepository;
import sgm.bookstory.BookStoryBackEnd.services.AuthService;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Override
    public User addUser(User user) {
        if(authRepository.existsById(user.getUserEmail())) throw new BookStoryApiException(HttpStatus.BAD_REQUEST, "User email already exists! - User Email: "+user.getUserEmail());
        // 기본값 설정
        user.setUserStatus(UserStatus.logout);
        user.setLastLoginTime(new Timestamp(new Date().getTime()));
        return authRepository.save(user);
    }
    @Override
    public User removeUser(User user){
        if(!authRepository.existsById(user.getUserEmail())) throw new BookStoryApiException(HttpStatus.BAD_REQUEST, "User email not exists!");
        authRepository.deleteById(user.getUserEmail());
        return user;
    }
    @Override
    public User userLogin(User user){
        User findUser = authRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        findUser.setUserStatus(UserStatus.login);
        return authRepository.save(findUser);
    }
    @Override
    public User userLogout(User user){
        User findUser = authRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        findUser.setUserStatus(UserStatus.logout);
        return authRepository.save(findUser);
    }
}
