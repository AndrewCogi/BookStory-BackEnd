package sgm.bookstory.BookStoryBackEnd.services.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.Book;
import sgm.bookstory.BookStoryBackEnd.entities.Favorite;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.enums.UserStatus;
import sgm.bookstory.BookStoryBackEnd.models.BookStoryApiException;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.repos.UserRepository;
import sgm.bookstory.BookStoryBackEnd.services.FavoriteService;
import sgm.bookstory.BookStoryBackEnd.services.UserService;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository authRepository;
    @Autowired
    private FavoriteService favoriteService;
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
        // 유저 삭제 전, 유저와 관련된 favorites 모두 가져오기
        List<Favorite> userFavorites = favoriteService.getAllFavoriteByUser_UserEmail(user.getUserEmail());
        // favorites에서 Book 정보만 뽑아내기
        List<Book> userBooks = new ArrayList<>();
        for(Favorite f : userFavorites) userBooks.add(f.getBook());
        System.out.println("UserBooks: "+userBooks);
        // 유저 삭제
        authRepository.deleteById(user.getUserEmail());
        // 관련 책에 대해 favoriteCount 최신화
        for(Book b : userBooks) favoriteService.updateBookInfo_MANUAL(b);
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

    @Override
    public ResponseModel<Boolean> isValidUser(String userEmail, String authHeader){
        String accessToken = authHeader.substring(7);

        System.out.println("userEmail: "+userEmail);
        System.out.println("Authentication: Bearer "+accessToken);

        // AWS Cognito 설정
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard().build();
        System.out.println("cognitoClient: "+cognitoClient);
        try {
            // 토큰 유효성 검사
            GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
            System.out.println("getUserRequest: "+getUserRequest);
            GetUserResult getUserResult = cognitoClient.getUser(getUserRequest);
            System.out.println("getUserResult: "+getUserResult);
            System.out.println("getUserName: "+getUserResult.getUsername());
            // 사용자의 이메일 비교
            if(userEmail.equals(extractEmailFromAttributes(getUserResult.getUserAttributes()))){
                // 사용자의 토큰 만료 검사
                Date currentDate = new Date(new Timestamp(System.currentTimeMillis()).getTime());
                Date userDate = new Date(getLastStatusUpdateTime(userEmail).getTime());
                System.out.println("currentDate: "+currentDate);
                System.out.println("userDate: "+userDate);
                long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime()-userDate.getTime());
                System.out.println("diffInMinutes: "+diffInMinutes);
                if(diffInMinutes < 1){
                    System.out.println("[userEmail compare Result] : Success! (UserEmail: "+userEmail+")");
                    return new ResponseModel<>(HttpStatus.OK.value(), "Success", true);
                }
                // 토큰이 만료됨
                else {
                    throw new BookStoryApiException(HttpStatus.FORBIDDEN, "Token Expired");
                }
            }
            // 사용자가 일치하지 않음
            else {
                System.out.println("[isValidUser Result] : User Mismatch! (UserEmail: "+userEmail+", TokenEmail: "+extractEmailFromAttributes(getUserResult.getUserAttributes())+")");
                throw new BookStoryApiException(HttpStatus.UNAUTHORIZED, "User Mismatch");
            }
        } catch (NotAuthorizedException e) {
            // 토큰이 유효하지 않을 때의 처리
            System.out.println("[isValidUser Result] : User UnAuthorized!");
            throw new BookStoryApiException(HttpStatus.UNAUTHORIZED, "User UnAuthorized");
        }
    }

    @Override
    public Timestamp getLastStatusUpdateTime(String userEmail) {
        final User findUser = authRepository.findByUserEmail(userEmail).orElseThrow(() -> new BookStoryApiException(HttpStatus.BAD_REQUEST, "User not found!"));
        return findUser.getLastStatusUpdateTime();
    }

    private String extractEmailFromAttributes(List<AttributeType> attributes) {
        for (AttributeType attribute : attributes) {
            if ("email".equals(attribute.getName())) {
                return attribute.getValue();
            }
        }
        return null;
    }
}