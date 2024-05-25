//package nl.fontys.sem6.auth.security.services;
//
////import nl.fontys.sem6.mang.model.User;
////import nl.fontys.sem6.mang.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//	User user = userRepository.findByUsername(username).orElseThrow(
//	        () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
//
//	    return UserPrincipal.build(user);
//	}
//}
package nl.fontys.sem6.auth.security.services;

import nl.fontys.sem6.auth.config.UserDetailsRequest;
import nl.fontys.sem6.auth.config.UserDetailsResponse;
import nl.fontys.sem6.auth.message.request.LoginForm;
import nl.fontys.sem6.auth.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserDetailsRequest request = new UserDetailsRequest(username);
//		UserDetailsResponse response = (UserDetailsResponse) rabbitTemplate.convertSendAndReceive("userDetailsQueue", request);
//
//		if (response == null) {
//			throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
//		}
//
//		return UserPrincipal.build(response);
//	}
     @Autowired
     private RestTemplate restTemplate;
//
////	@Value("${main.service.url}")
//	private String mainServiceUrl;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserDetailsResponse response = restTemplate.getForObject("http://localhost:8081/api/user/" + username, UserDetailsResponse.class);
//
//		if (response == null) {
//			throw new UsernameNotFoundException("User Not Found with -> username : " + username);
//		}
//
//		return UserPrincipal.build(response);
//	}
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsResponse.class);


	@Value("${main.service.url}")
	private String mainServiceUrl;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String url = "http://localhost:8081/api/user/verify";
		logger.debug("Calling MainService with URL: {}", url);

		LoginForm loginRequest = new LoginForm();
		loginRequest.setUsername(username);

		UserDetailsResponse userResponse;
		try {
			HttpEntity<LoginForm> request = new HttpEntity<>(loginRequest);
			ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, UserDetailsResponse.class);
			logger.debug("Response from MainService: {}", response);
//
//			if (!response.getStatusCode().is2xxSuccessful()) {
//				throw new UsernameNotFoundException("User Not Found with username: " + username);
//			}
//
//			userResponse = response.getBody();
//		} catch (Exception e) {
//			logger.error("User not found in MainService: {}", username, e);
//			throw new UsernameNotFoundException("User Not Found with username: " + username);
//		}
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new UsernameNotFoundException("User Not Found with username: " + username);
			}

			userResponse = response.getBody();
			if (userResponse == null) {
				throw new UsernameNotFoundException("User response is null for username: " + username);
			}
		}   catch (Exception e) {
			logger.error("User not found in MainService: {}", username, e);
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}

		return UserPrincipal.build(userResponse);
	}

//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		String url = "http://localhost:8081/api/user/" + username;
//		UserDetailsResponse userResponse;
//		try {
//			userResponse = restTemplate.getForObject(url, UserDetailsResponse.class);
//			logger.debug("User found in MainService: {}", userResponse.getUsername());
//		} catch (Exception e) {
//			logger.error("User not found in MainService: {}", username, e);
//			throw new UsernameNotFoundException("User Not Found with username: " + username);
//		}
//
//		return UserPrincipal.build(userResponse);


//
//		UserDetailsResponse userDetailsResponse;
//		try {
//			userDetailsResponse = restTemplate.getForObject(url, UserDetailsResponse.class);
//		} catch (Exception e) {
//			throw new UsernameNotFoundException("User Not Found with username: " + username);
//		}
//
//		return UserPrincipal.build(userDetailsResponse);
//	}

}


