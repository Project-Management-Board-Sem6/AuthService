package nl.fontys.sem6.auth.security.services;

import nl.fontys.sem6.auth.config.UserDetailsResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
	private String username;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal build(UserDetailsResponse user) {
		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
//		public static UserPrincipal build(UserDetailsResponse user) {
//			List<GrantedAuthority> authorities = user.getRoles().stream()
//					.map(role -> new SimpleGrantedAuthority(role))
//					.collect(Collectors.toList());

		return new UserPrincipal(
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities
		);
	}

	// Other required methods from UserDetails interface

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}



//package nl.fontys.sem6.auth.security.services;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import nl.fontys.sem6.mang.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserPrincipal implements UserDetails {
//
//	private static final long serialVersionUID = 1L;
//
//	private Long id;
//
//	private String username;
//
//	private String email;
//
//	@JsonIgnore
//	private String password;
//
//	private Collection<? extends GrantedAuthority> authorities;
//
//	public UserPrincipal(Long id, String username, String email, String password,
//			Collection<? extends GrantedAuthority> authorities) {
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.authorities = authorities;
//	}
//
//	public static UserPrincipal build(User user) {
//		List<GrantedAuthority> authorities = user
//			.getRoles()
//			.stream()
//			.map(role -> new SimpleGrantedAuthority(role.getName().name())
//		).collect(Collectors.toList());
//
//		return new UserPrincipal(
//				user.getId(),
//				user.getUsername(),
//				user.getEmail(),
//				user.getPassword(),
//				authorities
//		);
//	}
//
//	public Long getId() {
//        return id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserPrincipal other = (UserPrincipal) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
//}
