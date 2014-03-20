package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.List;

/**
 * @author Moritz Schulze
 */
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
