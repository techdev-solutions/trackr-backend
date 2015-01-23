package de.techdev.trackr.domain.translations;

import de.techdev.trackr.domain.employee.settings.SettingsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TranslationControllerTest {

    @InjectMocks
    private TranslationController translationController;

    @Mock
    private LocaleResolver localeResolver;

    @Mock
    private SettingsRepository settingsRepository;

    @Test
    public void testGetTranslations() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        translationController.getTranslations(Locale.ENGLISH, response);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), isNotEmpty());
    }

    @Test
    public void testSetTranslations() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        translationController.setLocale(Locale.ENGLISH, request, response, () -> "admin");

        verify(localeResolver, atLeastOnce()).setLocale(request, response, Locale.ENGLISH);
    }
}