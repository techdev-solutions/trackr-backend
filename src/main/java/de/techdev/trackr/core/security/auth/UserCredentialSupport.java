package de.techdev.trackr.core.security.auth;
import static java.util.Arrays.asList;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.domain.employee.login.AuthorityRepository;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;

@Slf4j
@Getter
@Setter
public class UserCredentialSupport {

	public static final String USER_NOT_FOUND = "User not found.";
	public static final String USER_CREATED = "Your user has been created and is now waiting to be activated.";


	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private EmployeeRepository employeeRepository;


	Function<Map<String,String>,Employee> creator = UserCredentialSupport::createDeactivatedEmployeeWith;
	Predicate<Map<String,String>> autoCreationDecider = attrs->true; 
	/**
	 * If the mail attribute matches the company's {@link mail suffix #mailSuffix} new credentials and creates a user if so.
	 * @param attributes  attribute map for creation
	 * 
	 * @see #createEmployee(String, String, String)
	 */
	public void handleAutoCreateCredential(Map<String, String> attributes) {
		if (autoCreationDecider.test(attributes)) {
			log.debug("New company user with email {} found.", attributes.get("email"));
			Employee employee = creator.apply(attributes);
			employeeRepository.saveAndFlush(employee);
		}
	}

	


	public Credential findByMail(String email) {
		return credentialRepository.findByEmail(email);
	}




	public Credential findByMailChecked(String email) {
		Credential credential = findByMail(email);
		if (credential == null) {
			throw new UsernameNotFoundException(USER_NOT_FOUND);
		}
		return credential;
	}




	public static Predicate<Map<String,String>> hasMailSuffix(String mailSuffix) {
		return attributes-> attributes.get("email").endsWith(mailSuffix);
	}

	static public Employee createDeactivatedEmployeeWith(Map<String, String> attributes) {
		Employee employee = new Employee();
		Credential credential = new Credential();
		employee.setFirstName(attributes.get("first"));
		employee.setLastName(attributes.get("last"));
		employee.setFederalState(FederalState.BERLIN);
		credential.setEmail(attributes.get("email"));
		
		credential.setEnabled(false);
		credential.setEmployee(employee);
		credential.setLocale("en");
		employee.setCredential(credential);
		return employee;
	}

	static  protected Employee createDeactivatedEmployee(String email, String first, String last) {
		Employee employee = new Employee();
		Credential credential = new Credential();
		employee.setFirstName(first);
		employee.setLastName(last);
		employee.setFederalState(FederalState.BERLIN);
		credential.setEmail(email);
		credential.setEnabled(false);
		credential.setEmployee(employee);
		credential.setLocale("en");
		employee.setCredential(credential);
		return employee;
	}

	public Employee createActivatedEmployee(Map<String,String> attributes){
		return createActivatedEmployeeWithRole(attributes, "ROLE_EMPLOYEE");
	}

	private Employee createActivatedEmployeeWithRole(Map<String, String> attributes,
			String roleEmployee) {
		Employee employee = createDeactivatedEmployee(attributes.get("email"), attributes.get("first"), attributes.get("last"));
		Credential credential = employee.getCredential();
		credential.setEnabled(true);
		credential.setAuthorities(
				asList(authorityRepository.findByAuthority(roleEmployee)));
		return employee;
	}




	public Credential createIfNotFound(String email,
			Supplier<Map<String, String>> attributCreator) {
		Credential credential = findByMail(email);
		if (credential == null) {
			Map<String, String> attributes = attributCreator.get();
			handleAutoCreateCredential(attributes);
			credential = findByMailChecked(email);
		}
		if (!credential.getEnabled()) {
			log.debug("User {} is disabled, preventing log in.", email);
			throw new UsernameNotActivatedException("User " + email + " is deactivated. Please wait for activation.");
		}
		return credential;
	}
	
	public static UserCredentialSupport withCreateActivatedEmployees(){
		UserCredentialSupport userCreationSupport = new UserCredentialSupport();
		userCreationSupport.setCreator(userCreationSupport::createActivatedEmployee);
		return userCreationSupport;
	}

}