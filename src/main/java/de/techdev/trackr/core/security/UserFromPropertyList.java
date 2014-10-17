package de.techdev.trackr.core.security;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.TrackrUser;

@Slf4j
final class UserFromPropertyList implements
UserDetailsService {

	@Value("${auth.users}")
	private String[] authUsers;

	private Map<String,TrackrUser> users;

	public Map<String, TrackrUser> users(){
		if(users==null){
			users = new HashMap<>();
			Arrays.stream(authUsers).forEach(this::addUserFromSpec);
		}
		return users;
	}

	private void addUserFromSpec(String userSpec) {
		String[] details = userSpec.split(":");
		try{
			users.put(details[0],  new TrackrUser(details[0], details[2], true, asList(new Authority(details[1])), 0L, Locale.ENGLISH));
		} catch(ArrayIndexOutOfBoundsException ex){
			log.error("unsuported user spec {} use format <email>:<authority>:<password>", userSpec);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TrackrUser user = users().get(username);
		if(user == null){
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}