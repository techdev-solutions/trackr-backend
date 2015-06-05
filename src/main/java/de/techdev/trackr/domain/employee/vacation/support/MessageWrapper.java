package de.techdev.trackr.domain.employee.vacation.support;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class MessageWrapper {

    private final Message message;

    public MessageWrapper(Message message) {
        this.message = message;
    }

    /**
     * Extract the content of a Mail as a String depending on the content type.
     * text/plain -> directly
     * multipart/mime -> only the text/plain part if it exists, null otherwise
     * multipart -> the body
     * @return
     * @throws IOException
     * @throws MessagingException
     */
     String extractContentAsString() throws IOException, MessagingException {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        }
        if(content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                if (multipart.getBodyPart(i).getContentType().startsWith("text/plain")) {
                    return (String)multipart.getBodyPart(i).getContent();
                }
            }
            return null;
        }
        if (content instanceof Multipart) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ((Multipart) content).writeTo(outputStream);
            return new String(outputStream.toByteArray(), "UTF-8");
        }
        throw new IllegalArgumentException("Incompatible message type.");
    }

    /**
     * Extracts the sender of an email if it is an InternetAddress.
     * @return The address or null if it's not a techdev address.
     * @throws MessagingException
     */
    String getSender() throws MessagingException {
        String from = null;
        Address[] fromArray = message.getFrom();
        if (fromArray.length > 0) {
            Address address = message.getFrom()[0];
            if (address instanceof InternetAddress) {
                from = ((InternetAddress) address).getAddress();
                if (!from.endsWith("techdev.de")) {
                    from = null;
                }
            }
        }
        return from;
    }
}
