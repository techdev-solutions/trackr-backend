package de.techdev.trackr.core.pdf;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Renders a thymeleaf template as a String containing HTML.
 *
 * @author Moritz Schulze
 */
@Slf4j
public class ThymeleafRenderer {

    private TemplateEngine templateEngine;

    private String templatePath;

    private String templateSuffix;

    public ThymeleafRenderer(String templatePath, String templateSuffix) {
        this.templatePath = templatePath;
        this.templateSuffix = templateSuffix;
        setUpTemplateEngine();
    }

    private void setUpTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        log.debug("Configuring template resolver with: path {}, suffix {}", templatePath, templateSuffix);
        templateResolver.setPrefix(templatePath);
        templateResolver.setSuffix(templateSuffix);
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * Render the given template to HTML with the context.
     *
     * @param templateName The template to render.
     * @param context      The context to use when rendering the template.
     * @return The HTML content of the rendered template as a String.
     */
    public String renderTemplateToHtml(String templateName, Context context) {
        return templateEngine.process(templateName, context);
    }

}
