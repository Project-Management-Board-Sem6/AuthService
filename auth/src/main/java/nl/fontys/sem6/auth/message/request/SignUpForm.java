package nl.fontys.sem6.auth.message.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm {

	@NotBlank
	@Size(min = 3, max = 50)	
	private String username;

	@NotBlank
	@Size(max = 60)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 40)	
	private String password;

    public SignUpForm(){

	}

	private SignUpForm(String username, String password, String email) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public static SignUpForm createSignUpForm(String username, String password, String email) {
		return new SignUpForm(username, password, email);
	}
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}