package com.langrsoft.reporting;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class MailDestination {

    private final String address;

    public MailDestination(String address) {
        this.address = address;
    }

    private static Endpoint createEndpoint(String address) {
        throw new MailDestinationException(
                String.format("unable to connect to LDAP server for address %s", address));
    }

    public String getAddress() {
        return address;
    }

    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }

    public static Endpoint getEndpoint(MailDestination destination) {
        return createEndpoint(destination.getAddress());
    }

}
