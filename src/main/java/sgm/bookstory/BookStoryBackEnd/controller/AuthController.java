package sgm.bookstory.BookStoryBackEnd.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @PostMapping("/validate-token")
    @CrossOrigin
    public Map<String, String> validateToken(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");

        // AWS Cognito 설정
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard().build();
        Map<String, String> response = new HashMap<>();

        try {
            // 토큰 유효성 검사
            GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
            GetUserResult getUserResult = cognitoClient.getUser(getUserRequest);

            // 유효한 사용자 정보
            response.put("message", "Token is valid");
            response.put("username", getUserResult.getUsername());
        } catch (NotAuthorizedException e) {
            // 토큰이 유효하지 않을 때의 처리
            response.put("message", "Token is not valid");
        }

        return response;
    }
}
