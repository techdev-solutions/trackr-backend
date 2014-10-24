package de.techdev.trackr.core.security;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import de.techdev.trackr.core.security.auth.UserCredentialSupport;
import de.techdev.trackr.core.security.auth.UsernameNotActivatedException;
import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.TrackrUser;

@Slf4j
public class UserFromPropertyList implements
UserDetailsService {
	private static final String SURNAME = "surname";
	private static final String GIVENNAME = "givenname";
	static final Pattern EMAIL_WITH_NAME_GROUPS = Pattern.compile("(?<" + GIVENNAME + ">\\w*)(\\.(?<" + SURNAME + ">\\w*))?@.*");
	@Value("${auth.users}")
	private String[] authUsers;

	@Autowired
	UserCredentialSupport userCreationSupport;
	
	private Map<String,TrackrUser> users;

	@PostConstruct
	private void initUsers() {
		users = new HashMap<>();
		Arrays.stream(authUsers).forEach(this::addUserFromSpec);
	}

	private void addUserFromSpec(String userSpec) {
		String[] details = userSpec.split(":");
		try {
			users.put(details[0],  new TrackrUser(details[0], details[2], true, asList(new Authority(details[1])), 0L, Locale.ENGLISH));
		} catch(ArrayIndexOutOfBoundsException ex){
			log.error("unsuported user spec {} use format <email>:<authority>:<password>", userSpec);
		}
	}

	@Override
    @Transactional(noRollbackFor = UsernameNotActivatedException.class)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TrackrUser user = users.get(email);
		if(user == null){
			throw new UsernameNotFoundException("User not found");
		}
		Credential credential = userCreationSupport.createIfNotFound(email, ()->convertUserDetails(user));
		return new TrackrUser(user.getUsername(),user.getPassword(),true,credential.getAuthorities(),credential.getId(),user.getLocale());
	}

	static Map<String, String> convertUserDetails(TrackrUser user) {
		Map<String, String> attributeMap = new HashMap<>();
		Matcher emailAddress = EMAIL_WITH_NAME_GROUPS.matcher(user.getUsername());
		if(emailAddress.matches()){
			attributeMap.put("first", emailAddress.group(GIVENNAME));
			attributeMap.put("last", Objects.toString(emailAddress.group(SURNAME),""));
			attributeMap.put("email", user.getUsername());
			return attributeMap;	
		}
		throw new IllegalStateException("define a valid email adress");
	}
	
}