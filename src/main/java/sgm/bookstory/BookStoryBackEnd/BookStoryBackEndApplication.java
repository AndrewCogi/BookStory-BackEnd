package sgm.bookstory.BookStoryBackEnd;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class BookStoryBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoryBackEndApplication.class, args);
	}

	@GetMapping("/hello")
	public String HelloWorld(){
		return "Hello World";
	}

	@GetMapping("/")
	public ResponseEntity healthCheck(){
		return new ResponseEntity(HttpStatus.OK);
	}

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
