package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/address_book")
public class AddressBookController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    public Resources<ReducedEmployee> getAddressList(
            @PageableDefault(sort = "lastName") Pageable pageable
    ) {
        Page<Employee> pageOfEmployees = employeeRepository.findAllForAddressBook(pageable);
        List<ReducedEmployee> reducedEmployees = transformToReducedEmployees(pageOfEmployees.getContent());
        return new PagedResources<ReducedEmployee>(reducedEmployees, pageMetadataFromPage(pageOfEmployees));
    }

    /**
     * Transform a list of employees to a list of reduced employees
     *
     * @param listOfEmployees The list to transform
     * @return The transformed list.
     */
    protected List<ReducedEmployee> transformToReducedEmployees(List<Employee> listOfEmployees) {
        return listOfEmployees
                .stream()
                .map(ReducedEmployee::valueOf)
                .collect(Collectors.toList());
    }

    protected PagedResources.PageMetadata pageMetadataFromPage(Page page) {
        return new PagedResources.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }
}
