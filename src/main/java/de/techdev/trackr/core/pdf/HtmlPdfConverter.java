package de.techdev.trackr.core.pdf;

import com.itextpdf.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Moritz Schulze
 */
public class HtmlPdfConverter {

    private ITextRenderer renderer;

    public HtmlPdfConverter() {
        renderer = new ITextRenderer();

        //This can be used to set a font?
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        ClassPathResource regular = new ClassPathResource("/META-INF/fonts/LiberationSerif-Regular.ttf");
//        fontResolver.addFont(regular.getURL().toString(), BaseFont.IDENTITY_H, true);
    }

    public byte[] renderHtmlToPdf(String htmlContent) throws PdfCreationException {
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        byte[] pdfAsByteArray;
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            renderer.createPDF(bos);
            pdfAsByteArray = bos.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new PdfCreationException(e);
        }
        return pdfAsByteArray;
    }
}
