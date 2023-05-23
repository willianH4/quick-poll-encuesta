package com.williamhdz.quickpoll.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.williamhdz.quickpoll.domain.User;
import com.williamhdz.quickpoll.repository.UserRepository;

import jakarta.inject.Inject;

public class QuickPollUserDetailsService implements UserDetailsService {
	
	@Inject UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with the username %s doesn't exist", username));
		}
		
		// Create a granted authority based on user's role.
		// Can't pass null authorities to user. Hence initialize with an empty arraylist
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(user.getAdmin()) {
			authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		}
		
		// Create a UserDetails object from the data
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
		user.getPassword(), authorities);
		return userDetails;
	}
	
}
