package reporting;

import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReportMailerTest {
    private ReportMailer mailer;

    @Test
    public void canConstructMailMessage() throws MessagingException, IOException {
        var report = new Report() {
            @Override
            public String getText() {
                return "report text";
            }

            @Override
            public String getName() {
                return "report name";
            }
        };
        var session = Session.getDefaultInstance(new Properties());

        var message = ReportMailer.constructMailMessageTo("a@b.com", report, session);

        assertThat(message.getFrom()).containsExactly(new InternetAddress(ReportMailer.FROM));
        assertThat(message.getSubject()).isEqualTo("report name");
        assertThat(message.getContent()).isEqualTo("report text");
        assertThat(message.getRecipients(Message.RecipientType.TO)).containsExactly(new InternetAddress("a@b.com"));
    }

    @Before
    public void setup() {
        MailDestination[] destinations = { new MailDestination("a@b.com") };
        mailer = new ReportMailer(destinations) {
            @Override
            protected Endpoint getEndpoint(MailDestination destination) {
                return new Endpoint();
            }
        };
    }

    @Test
    public void constructStuff() throws MessagingException {
        Report report = new Report() {
            @Override public String getText() { return "text"; }
            @Override public String getName() { return "name"; }
        };

        mailer.mailReport(report);
    }
}
