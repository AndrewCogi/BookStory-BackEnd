package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.enums.UserStatus;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.repos.UserRepository;
import sgm.bookstory.BookStoryBackEnd.services.UserService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository authRepository;
    @Override
    public User addUser(User user) {
        // 기존에 존재하는 계정이라도 cognito에서 회원가입에 성공했다면 여기까지 올 수 있기 때문에 예외처리없이 진행
        // 유저의 상태 기본값 설정
        user.setUserStatus(UserStatus.signup);
        user.setLastStatusUpdateTime(new Timestamp(new Date().getTime()));
        // 계정 저장 후 계정정보 반환
        return authRepository.save(user);
    }
    @Override
    public User removeUser(User user){
        // 탈퇴하는 유저 찾기
        User findUser = authRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        // 유저가 포함된 views 자식관계 끊기
        List<View> views = findUser.getViews();
        for(View v : views) v.setUser(null);
        // 유저 삭제
        authRepository.delete(user);
        // 삭제된 유저 정보 반환
        return findUser;
    }
    @Override
    public User userLogin(User user){
        // 로그인하는 유저 찾기
        User findUser = authRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        // 유저의 상태 업데이트
        findUser.setUserStatus(UserStatus.login);
        findUser.setLastStatusUpdateTime(new Timestamp(new Date().getTime()));
        // 로그인한 유저의 정보 반환
        return authRepository.save(findUser);
    }
    @Override
    public User userLogout(User user){
        // 로그아웃하는 유저 찾기
        User findUser = authRepository.findByUserEmail(user.getUserEmail()).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        // 유저의 상태 업데이트
        findUser.setUserStatus(UserStatus.logout);
        findUser.setLastStatusUpdateTime(new Timestamp(new Date().getTime()));
        // 로그아웃한 유저의 정보 반환
        return authRepository.save(findUser);
    }
}