package de.techdev.trackr.core.security.auth;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.collections15.map.HashedMap;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import de.techdev.trackr.core.security.SecurityConfiguration;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.domain.employee.login.Authority;
import de.techdev.trackr.domain.employee.login.AuthorityRepository;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;


/**
 * @author Moritz Schulze
 * @author Lars Sadau
 */
@RunWith(MockitoJUnitRunner.class)
public class UserCredentialSupportTest {
	private static final Authority ROLE_EMPLOYEE = new Authority(SecurityConfiguration.ROLE_EMPLOYEE);
	@Mock
	private AuthorityRepository authorityRepository;
	@Mock
	private CredentialRepository credentialRepository;
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	UserCredentialSupport userCreationSupport;

	String MORITZ_MAIL = moritz().get("email");
	@Before
	public void injectMocks(){
		initMocks(this);
	}

	@Test(expected=UsernameNotFoundException.class)
	public void findByMailCheckedFails() {
		when(credentialRepository.findByEmail(any())).thenReturn(null);
		userCreationSupport.findByMailChecked("lars.sadau@carneios.de");
	}


	@Test
	public void createDeactivatedEmployee() throws Exception {
		Employee employee = UserCredentialSupport.createDeactivatedEmployeeWith(moritz());
		assertThat(employee, hasAttributes(moritz()));
	}

	@Test
	public void createActivatedEmployee() throws Exception {
		when(authorityRepository.findByAuthority(SecurityConfiguration.ROLE_EMPLOYEE)).thenReturn(ROLE_EMPLOYEE);

		Employee employee = userCreationSupport.createActivatedEmployee(moritz());

		assertThat(employee, hasAttributes(moritz()));
		assertThat(employee.getCredential().getAuthorities(), hasItem(ROLE_EMPLOYEE));
	}


	@Test(expected=UsernameNotActivatedException.class)
	public void createIfNotFound_noCreationIfFound_butExceptionWhenNotEnabled(){
		when(credentialRepository.findByEmail(MORITZ_MAIL)).thenReturn(deactivated());
		
		userCreationSupport.createIfNotFound(MORITZ_MAIL,failingSupplier());
	}

	@Test
	public void createIfNotFound_noCreationIfFound(){
		Credential one = activated();
 		
		when(credentialRepository.findByEmail(MORITZ_MAIL)).thenReturn(one);
		
		assertThat(userCreationSupport.createIfNotFound(MORITZ_MAIL,failingSupplier()),is(one));
	}
	
	@Test
	public void createIfNotFound(){
		Credential moritzCredentials = activated();
		when(credentialRepository.findByEmail(MORITZ_MAIL)).thenReturn(null,moritzCredentials);
		@SuppressWarnings("unchecked")
		Supplier<Map<String, String>> getMoritz = mock(Supplier.class);
		when(getMoritz.get()).thenReturn(moritz());
		Predicate<Map<String, String>> autoCreationDecider = mock(Predicate.class);
		when(autoCreationDecider.test(any())).thenReturn(true);
		Function<Map<String, String>, Employee> creator = mock(Function.class);
		when(creator.apply(any())).thenReturn(mock(Employee.class));
		
		userCreationSupport.setAutoCreationDecider(autoCreationDecider );
		userCreationSupport.setCreator(creator);
		assertThat(userCreationSupport.createIfNotFound(MORITZ_MAIL,getMoritz),is(moritzCredentials));
		verify(getMoritz).get();
		verify(creator).apply(Mockito.eq(moritz()));
		verify(autoCreationDecider).test(Mockito.eq(moritz()));
	}

	private Credential deactivated() {
		return mock(Credential.class);
	}
	
	private Credential activated() {
		Credential credential = mock(Credential.class);
		when(credential.getEnabled()).thenReturn(true);
		return credential;
	}


	private Map<String, String> moritz() {
		Map<String, String> attributes = new HashedMap<String, String>();
		attributes.put("first","Moritz");
		attributes.put("last","Schulze");
		attributes.put("email","moritz.schulze@techdev.de");
		return attributes ;
	}

	private Supplier<Map<String, String>> failingSupplier() {
		Supplier<Map<String,String>> attributs=() -> {
			fail("supplier shall not be called if successfull"); 
			return null; 
		};
		return attributs;
	}

	Matcher<Employee> hasAttributes(Map<String, String>  attributes){
		return new TypeSafeMatcher<Employee>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("has attributes");

			}

			@Override
			protected boolean matchesSafely(Employee employee) {
				return  Objects.equals(employee.getFirstName(), attributes.get("first")) &&
						Objects.equals(employee.getLastName(), attributes.get("last")) &&
						nonNull(employee.getCredential()) &&
						Objects.equals(employee.getCredential().getEmail(),  attributes.get("email"));

			}
		};

	}


}
