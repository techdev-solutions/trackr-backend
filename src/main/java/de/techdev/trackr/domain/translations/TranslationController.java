package de.techdev.trackr.domain.translations;

import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/translations")
public class TranslationController {

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private CredentialRepository credentialRepository;

    @RequestMapping(method = RequestMethod.GET)
    public void getTranslations(Locale locale, HttpServletResponse response) {
        ClassPathResource translationFile = new ClassPathResource("/i18n/trackr-" + locale.toLanguageTag() + ".json");
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            IOUtils.copy(translationFile.getInputStream(), response.getWriter(), "UTF-8");
            response.setStatus(200);
            response.getWriter().close();
        } catch (IOException e) {
            throw new IllegalStateException("Could not open translation file");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String setLocale(@RequestParam("locale") Locale locale, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        localeResolver.setLocale(request, response, locale);
        Credential credential = credentialRepository.findByEmail(principal.getName());
        credential.setLocale(locale.toLanguageTag());
        credentialRepository.save(credential);
        return "Ok.";
    }
}
