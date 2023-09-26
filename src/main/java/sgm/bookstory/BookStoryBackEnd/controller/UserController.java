package sgm.bookstory.BookStoryBackEnd.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.User;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.UserService;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService authService;
    @PostMapping("/login")
    public ResponseModel<User> loginUser(@RequestBody User user){
        final User loginUser = authService.userLogin(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Login Complete", loginUser);
    }
    @PostMapping("/logout")
    public ResponseModel<User> logoutUser(@RequestBody User user){
        final User logoutUser = authService.userLogout(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Logout Complete", logoutUser);
    }
    @PostMapping("/add")
    public ResponseModel<User> addUser(@RequestBody User user){
        final User savedUser = authService.addUser(user);
        return new ResponseModel<>(HttpStatus.OK.value(), "User Added", savedUser);
    }
    @PostMapping("/remove")
    public ResponseModel<User> removeUser(@RequestBody User user, @RequestHeader("Authorization") String authHeader){
        if(authService.isValidUser(user.getUserEmail(), authHeader)){
            final User removedUser = authService.removeUser(user);
            return new ResponseModel<>(HttpStatus.OK.value(), "User Removed", removedUser);
        }
        // 사용자가 일치하지 않음
        else {
            return new ResponseModel<>(HttpStatus.UNAUTHORIZED.value(), "User mismatch", null);
        }
    }

//    @PostMapping("/validate-token")
//    @CrossOrigin
//    public Map<String, String> validateToken(@RequestBody Map<String, String> request) {
//        String accessToken = request.get("accessToken");
//
//        // AWS Cognito 설정
//        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard().build();
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            // 토큰 유효성 검사
//            GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
//            System.out.println("getUserRequest (vt): "+getUserRequest);
//            GetUserResult getUserResult = cognitoClient.getUser(getUserRequest);
//            System.out.println("getUserResult (vt): "+getUserResult);
//
//            // 유효한 사용자 정보
//            response.put("message", "Token is valid");
//            response.put("username", getUserResult.getUsername());
//        } catch (NotAuthorizedException e) {
//            // 토큰이 유효하지 않을 때의 처리
//            response.put("message", "Token is not valid");
//        }
//
//        return response;
//    }
}
