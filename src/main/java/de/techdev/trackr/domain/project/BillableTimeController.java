package de.techdev.trackr.domain.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/billableTimes")
public class BillableTimeController {

    @Autowired
    @Qualifier("defaultConversionService")
    private ConversionService conversionService;

    @Autowired
    private BillableTimeRepository billableTimeRepository;

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @ResponseBody
    @RequestMapping(value = "/findEmployeeMappingByProjectAndDateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> findEmployeeMappingByProjectAndDateBetween(@RequestParam("project") String projectId,
                                                           @RequestParam("start") Date start,
                                                           @RequestParam("end") Date end) {
        Project project = conversionService.convert(Long.valueOf(projectId), Project.class);
        List<BillableTime> billableTimes = billableTimeRepository.findByProjectAndDateBetweenOrderByDateAsc(project, start, end);
        return billableTimes.stream().collect(groupingBy(bt -> bt.getEmployee().fullName(), summingInt(BillableTime::getMinutes)));
    }
}
