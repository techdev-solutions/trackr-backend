package de.techdev.trackr.core.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class PdfRenderer {

//    @Value("${pdf.templatePath:META-INF/pdfTemplates}")
    @Value("pdfTemplates/")
    private String templatePath;

//    @Value("${pdf.templateSuffix:pdf.templateSuffix:.html}")
    @Value(".html")
    private String templateSuffix;

    private ThymeleafRenderer thymeleafRenderer;
    private HtmlPdfConverter htmlPdfConverter;

    public PdfRenderer() {
        htmlPdfConverter = new HtmlPdfConverter();
    }

    @PostConstruct
    public void setUpRenderer() {
        thymeleafRenderer = new ThymeleafRenderer(templatePath, templateSuffix);
    }

    public byte[] renderPdf(String templateName, Context context) throws PdfCreationException {
        log.debug("Rendering template {}", templateName);
        String htmlContent = thymeleafRenderer.renderTemplateToHtml(templateName, context);
        return htmlPdfConverter.renderHtmlToPdf(htmlContent);
    }
}
