package javax.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class EmailOTPVerification {
    
    public static String generatedOtp;
    
    

    // Generate a random 6-digit OTP
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        generatedOtp = String.valueOf(otp);
        return String.valueOf(otp);
    }

    // Send OTP to the provided email address
    public static void sendOTP(String recipientEmail) {
        final String senderEmail = "contactwithsafanur@gmail.com"; // Gmail Account
        final String password = "jjbi hjnt utyc zoak "; // Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        props.put("mail.smtp.port", "587"); // Gmail SMTP server Port

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("OTP Verification");
            message.setText("Your OTP for verification is: " + generateOTP());

            Transport.send(message);
            System.out.println("OTP sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Verify the entered OTP
    public static boolean verifyOTP(String enteredOTP, String actualOTP) {
        return enteredOTP.equals(actualOTP);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get recipient email from the user
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine();

        // Generate OTP and send it to the email
        String actualOTP = generateOTP();
        sendOTP(email);

        // Prompt user to enter the OTP
        System.out.print("Enter the OTP sent to your email: ");
        String enteredOTP = scanner.nextLine();

        // Verify OTP
        if (verifyOTP(enteredOTP, actualOTP)) {
            System.out.println("OTP Verified successfully!");
            // Proceed with further actions
        } else {
            System.out.println("Invalid OTP. Please try again.");
            // Display error message to the user
        }

        scanner.close();
    }
}
