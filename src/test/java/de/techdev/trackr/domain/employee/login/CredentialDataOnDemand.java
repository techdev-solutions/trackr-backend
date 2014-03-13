package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Component
public class CredentialDataOnDemand {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    private List<Credential> data;

    protected SecureRandom rnd;

    public CredentialDataOnDemand() {
        rnd = new SecureRandom();
    }

    public void init() {
        employeeDataOnDemand.init();
        data = credentialRepository.findAll();
    }

    public Credential getRandomObject() {
        Credential obj = data.get(rnd.nextInt(data.size()));
        return credentialRepository.findOne(obj.getId());
    }
}
