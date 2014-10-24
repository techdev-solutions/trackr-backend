package de.techdev.trackr.core.security;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.regex.Matcher;

import org.junit.Test;

import de.techdev.trackr.core.security.UserFromPropertyList;
import de.techdev.trackr.domain.employee.login.TrackrUser;

public class UserFromPropertyListTest {

	@Test
	public void fullname_matches_EMAIL_WITH_FULLNAME() {
		Matcher matcher = UserFromPropertyList.EMAIL_WITH_NAME_GROUPS.matcher("lars.sadau@carneios.de");
		assertTrue(matcher.matches());
		assertEquals("lars",matcher.group("givenname"));
		assertEquals("sadau",matcher.group("surname"));
	}
	
	@Test
	public void onlySurname_matches_EMAIL_WITH_FULLNAME() {
		Matcher matcher = UserFromPropertyList.EMAIL_WITH_NAME_GROUPS.matcher("admin@carneios.de");
		assertTrue(matcher.matches()); 
		assertEquals("admin",matcher.group("givenname"));
		assertEquals(null,matcher.group("surname"));
	}
	@Test
	public void convertTrackUser() {
		TrackrUser user=mock(TrackrUser.class);
		String mail = "lars.sadau@carneios.de";
		when(user.getUsername()).thenReturn(mail);
		Map<String, String> details = UserFromPropertyList.convertUserDetails(user);
		assertEquals("lars",details.get("first"));
		assertEquals("sadau",details.get("last"));
		assertEquals(mail,details.get("email"));
	}


}
