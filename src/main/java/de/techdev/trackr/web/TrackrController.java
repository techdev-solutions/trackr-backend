package de.techdev.trackr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Moritz Schulze
 */
@Controller
public class TrackrController {

    /**
     * This is just a test method te be accessed in the example junit test.
     * TODO: Delete this method as soon as real examples are available.
     * @return The text "Welcome to trackr!"
     */
    @RequestMapping("/")
    public @ResponseBody String trackrGreeting() {
        return "Welcome to trackr!";
    }
}
