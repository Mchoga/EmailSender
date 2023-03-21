import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);

        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.enable", "true");

        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


//        props.put("mail.smtp.host", "smtp.mail.me.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.tlc.enable", "true");

        // Email authentication properties
        System.out.print("Login Email address: ");
        String username = input.nextLine();

        System.out.print("Login password: ");
        String password = input.nextLine();

        System.out.print("Recipient's Email address: ");
        String recipient = input.nextLine();

        System.out.print("Email subject: ");
        String subject = input.nextLine();

        System.out.println("Email Message: ");
        String messageBody = input.nextLine();
        

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Here we will create email message to the receiver
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // Content of the email
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageBody);

            // Attachment
            URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Eminem_-_Concert_for_Valor_in_Washington%2C_D.C._Nov._11%2C_2014_%282%29_%28Cropped%29.jpg/800px-Eminem_-_Concert_for_Valor_in_Washington%2C_D.C._Nov._11%2C_2014_%282%29_%28Cropped%29.jpg");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new URLDataSource(url);


            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("glpSoftware.jpg");

            // Creating the email multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);


            message.setContent(multipart);

            // Sending the email
            Transport.send(message);

            System.out.println("Email sent successfully.");
//            | IOException

        } catch (MessagingException | MalformedURLException e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }
    }
}
