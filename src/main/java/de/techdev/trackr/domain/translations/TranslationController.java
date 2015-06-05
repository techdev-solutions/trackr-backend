package de.techdev.trackr.domain.translations;

import de.techdev.trackr.domain.employee.Settings;
import de.techdev.trackr.domain.employee.SettingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/translations")
@Slf4j
public class TranslationController {

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private SettingsRepository settingsRepository;

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
            throw new IllegalStateException("Could not open translation file", e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String setLocale(@RequestParam("locale") Locale locale, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        localeResolver.setLocale(request, response, locale);
        Settings localeSettings = settingsRepository.findByTypeAndEmployee_Email(Settings.SettingsType.LOCALE, principal.getName());
        if (localeSettings == null) {
            log.error("Employee {} without locale settings.", principal.getName());
            return "Ok.";
        }
        localeSettings.setValue(locale.getLanguage());
        settingsRepository.save(localeSettings);
        return "Ok.";
    }
}
