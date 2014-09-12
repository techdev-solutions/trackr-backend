package de.techdev.trackr.domain.translations;

import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.junit.Before;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranslationControllerTest {

    @InjectMocks
    private TranslationController translationController;

    @Mock
    private LocaleResolver localeResolver;

    @Mock
    private CredentialRepository credentialRepository;

    @Before
    public void setUp() throws Exception {
        when(credentialRepository.findByEmail("admin")).thenReturn(new Credential());
    }

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

        verify(credentialRepository, atLeastOnce()).findByEmail("admin");
        verify(localeResolver, atLeastOnce()).setLocale(request, response, Locale.ENGLISH);
    }
}