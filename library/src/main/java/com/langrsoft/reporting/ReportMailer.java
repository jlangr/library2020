package com.langrsoft.reporting;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ReportMailer {
    private final MailDestination[] destinations;
    static final String FROM = "reports@langrsoft.com";

    public ReportMailer(MailDestination[] destinations) {
        this.destinations = destinations;
        if (destinations.length == 0)
            throw new ReportMailerException("dests required");
        for (int i = 0; i < destinations.length; i++)
            if (MailDestination.getEndpoint(destinations[i]) == null)
                throw new ReportMailerException("invalid endpoint");
    }

    public void mailReport(Report report) throws MessagingException {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getSMTPUser(), getSMTPPassword());
            }
        };
        Session session = Session.getDefaultInstance(System.getProperties(),
                authenticator);

        for (int i = 0; i < destinations.length; i++) {
            String toAddress = destinations[i].getAddress();
            Message message = constructMailMessage(toAddress, report, session);
            destinations[i].send(message);
        }
    }

    Message constructMailMessage(String toAddress, Report report, Session session)
            throws MessagingException {
        var content = report.getText();
        var subject = report.getName();

        var message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        message.setText(content);
        message.setFrom(new InternetAddress(FROM));
        message.setSubject(subject);
        return message;
    }

    private static String getSMTPPassword() {
        return System.getProperty("mail.smtp.password");
    }

    private static String getSMTPUser() {
        return System.getProperty("mail.smtp.user");
    }

    static void configureProperties() {
        final String address = "noreply@example.com";
        set("mail.transport.protocol", "smtp");
        set("mail.smtp.host", "sirius.lunarpages.com");
        set("mail.smtp.user", address);
        set("mail.smtp.from", address);
        set("mail.smtp.auth", "true");
        // this needs to be set via command line e.g. -Dmail.smtp.password=somepassword
        // you will need to set "mail.smtp.password"
        // you might want to set "mail.debug" to "true"
    }

    private static void set(String key, String value) {
        System.getProperties().put(key, value);
    }
}
