package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.FederalState;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/federalStates")
public class FederalStateController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FederalState> federalStates() {
        return asList(FederalState.values());
    }
}
