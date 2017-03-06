package de.techdev.trackr.domain.common

import de.techdev.trackr.domain.employee.Settings
import de.techdev.trackr.domain.employee.SettingsRepository
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.servlet.LocaleContextResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.springframework.web.util.WebUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class EmployeeSettingsLocaleResolver(private val settingsRepository: SettingsRepository) : LocaleContextResolver {

    override fun resolveLocale(request: HttpServletRequest): Locale {
        return getLocaleFromSessionOrAuthentication(request)
    }

    override fun setLocale(request: HttpServletRequest, response: HttpServletResponse, locale: Locale?) {
        if (locale != null) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale)
        }
    }

    override fun resolveLocaleContext(request: HttpServletRequest): LocaleContext {
        val localeContext = LocaleContext { getLocaleFromSessionOrAuthentication(request) }
        LocaleContextHolder.setLocaleContext(localeContext)
        return localeContext
    }

    override fun setLocaleContext(request: HttpServletRequest, response: HttpServletResponse, localeContext: LocaleContext?) {
        if (localeContext != null) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, localeContext.locale)
        }
    }

    /**
     * Tries to extract the locale from the session. If not present it extracts the locale from the logged in user. Defaults to english.
     * @param request The request
     * *
     * @return The extracted locale, default [java.util.Locale.ENGLISH].
     */
    protected fun getLocaleFromSessionOrAuthentication(request: HttpServletRequest): Locale {
        var locale: Locale? = WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) as Locale?
        if (locale == null) {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null) {
                val principal = authentication.principal
                when(principal) {
                    is User -> {
                        val username = principal.username
                        val localeSetting = settingsRepository.findByTypeAndEmployee_Email(Settings.SettingsType.LOCALE, username)
                        if (localeSetting != null) {
                            locale = Locale.forLanguageTag(localeSetting.value)
                        }
                    }
                }
            }
            //Either no authentication or admin user
            if (locale == null) {
                locale = Locale.ENGLISH
            }
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale)
        }
        LocaleContextHolder.setLocale(locale)
        return locale!! // TODO how can we circumvent this? We know it's not null from the defaulting above....
    }
}
