package ua.foxminded.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ua.foxminded.dto.UsersDto;

public class UsersDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final UsersDto usersDto;
	

	public UsersDetails(UsersDto usersDto) {
		this.usersDto = usersDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return user role
		return Collections.singletonList(new SimpleGrantedAuthority(usersDto.getUserType().toString()));
	}

	@Override
	public String getPassword() {
		return usersDto.getPassword();
	}

	@Override
	public String getUsername() {
		return usersDto.getNickName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public UsersDto getUsers() {
		return this.usersDto;
	}

}
