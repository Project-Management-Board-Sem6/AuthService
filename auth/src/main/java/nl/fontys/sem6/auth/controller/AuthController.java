package nl.fontys.sem6.auth.controller;


import nl.fontys.sem6.auth.config.UserRegistrationMessage;
import nl.fontys.sem6.auth.exception.AppException;
import nl.fontys.sem6.auth.message.request.LoginForm;
import nl.fontys.sem6.auth.message.request.SignUpForm;
import nl.fontys.sem6.auth.message.response.JwtResponse;
import nl.fontys.sem6.auth.message.response.ResponseMessage;
//import nl.fontys.sem6.mang.message.response.UserProfile;
import nl.fontys.sem6.auth.security.jwt.JwtProvider;
import nl.fontys.sem6.auth.message.MessageProducer;
import nl.fontys.sem6.auth.security.services.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import nl.fontys.sem6.auth.security.services.UserDetailsServiceImpl;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private MessageProducer messageProducer;

	@Autowired
	private RestTemplate restTemplate;


	@Value("${main.service.url}")
	private String mainServiceUrl;

//	@GetMapping("/user/me")
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<?> getCurrentUser() {
//
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userRepository
//				.findByUsername(auth.getName())
//				.orElseThrow(() -> new AppException("User Role not set."));
//		UserProfile userProfile = new UserProfile(auth.getName(), user.getEmail());
//
//		return new ResponseEntity<>(userProfile, HttpStatus.OK);
//	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Prepare the request to the main service
		String mainServiceUrl = "http://localhost:8081/api/user/verify"; // Replace with actual URL
		HttpEntity<LoginForm> request = new HttpEntity<>(loginRequest);

		// Send the request to the main service and get the response
//		ResponseEntity<String> response = restTemplate.exchange(mainServiceUrl, HttpMethod.POST, request, String.class);
		ResponseEntity<ResponseMessage> response = restTemplate.postForEntity(mainServiceUrl, request, ResponseMessage.class);
//		ResponseEntity<ResponseMessage> response = restTemplate.postForEntity(mainServiceUrl,HttpMethod.POST,request,ResponseMessage);

		if (response.getStatusCode().is2xxSuccessful()) {
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
		} else {
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		}


	 
//	    Authentication authentication = authenticationManager.authenticate(
//	        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//	    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//	    String jwt = jwtProvider.generateJwtToken(authentication);
//	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//		// Send a message to MainService about successful authentication
//		ResponseEntity<ResponseMessage> response = restTemplate.postForEntity(mainServiceUrl + "api/user/auth-status", authentication.getName(), ResponseMessage.class);
//
//		if (response.getStatusCode().is2xxSuccessful()) {
//			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
//		} else {
//			return ResponseEntity.status(response.getStatusCode()).body(new ResponseMessage("Failed to notify MainService"));
//		}

//	    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));


	}

	@GetMapping("/validate")
	public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
		String jwt = token; // Remove "Bearer " prefix

		if (jwtProvider.validateJwtToken(jwt)) {
			String username = jwtProvider.getUserNameFromJwtToken(jwt);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
		} else {
			return ResponseEntity.status(401).body("Invalid JWT token");
		}
	}




	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationMessage signUpRequest) {
		signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		messageProducer.sendUserRegistrationMessage(signUpRequest);
		return new ResponseEntity<>(new ResponseMessage("User registration request sent successfully!"), HttpStatus.OK);
	}
	 
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//		return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
//          HttpStatus.BAD_REQUEST);
//    }
//
//    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//    	return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
//		HttpStatus.BAD_REQUEST);
//    }
//
//    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//    		.orElseThrow(() -> new AppException("User Role not set."));
//
//    User user = User.builder()
//    		.username(signUpRequest.getUsername())
//    		.email(signUpRequest.getEmail())
//    		.password(passwordEncoder.encode(signUpRequest.getPassword()))
//    		.addRole(userRole)
//    		.build();
//
//    userRepository.save(user);
//
//    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
//  }
}
