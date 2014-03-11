package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Address;
import de.techdev.trackr.domain.Company;
import de.techdev.trackr.repository.CompanyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/createWithAddress", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company createWithAddress(@RequestBody @Valid CreateCompany createCompany, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        createCompany.getCompany().setAddress(createCompany.getAddress());
        return companyRepository.saveAndFlush(createCompany.getCompany());
    }

    /**
     * DTO for creating a company with an address.
     * <p>
     * This class <b>must</b> be static, otherwise the binding errors will not work correctly.
     */
    @Data
    protected static class CreateCompany {
        @Valid
        private Company company;

        @Valid
        private Address address;
    }
}
