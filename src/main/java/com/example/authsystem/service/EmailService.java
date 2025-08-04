package com.example.authsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            // In development, just log the token instead of sending actual email
            System.out.println("=".repeat(60));
            System.out.println("🔑 PASSWORD RESET TOKEN FOR DEVELOPMENT:");
            System.out.println("📧 Email: " + toEmail);
            System.out.println("🔐 Token: " + resetToken);
            System.out.println("⏰ Expires: 1 hour from now");
            System.out.println("=".repeat(60));
            
            // Try to send email, but don't fail if it doesn't work in development
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Password Reset Request");
            message.setText(buildPasswordResetEmailBody(resetToken));
            
            mailSender.send(message);
            System.out.println("✅ Email sent successfully!");
        } catch (Exception e) {
            // In development, don't throw error - just log that email sending failed
            System.out.println("⚠️ Email sending failed (this is normal in development): " + e.getMessage());
            System.out.println("💡 Use the token above to test password reset functionality");
        }
    }
    
    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            System.out.println("📧 Welcome email for: " + username + " (" + toEmail + ")");
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Welcome to Auth System!");
            message.setText(buildWelcomeEmailBody(username));
            
            mailSender.send(message);
            System.out.println("✅ Welcome email sent successfully!");
        } catch (Exception e) {
            // Log error but don't throw exception to avoid blocking registration
            System.out.println("⚠️ Welcome email sending failed (this is normal in development): " + e.getMessage());
        }
    }
    
    private String buildPasswordResetEmailBody(String resetToken) {
        return "Hello,\n\n" +
               "You have requested to reset your password. Please use the following token to reset your password:\n\n" +
               "Reset Token: " + resetToken + "\n\n" +
               "This token will expire in 1 hour.\n\n" +
               "If you did not request this password reset, please ignore this email.\n\n" +
               "Best regards,\n" +
               "Auth System Team";
    }
    
    private String buildWelcomeEmailBody(String username) {
        return "Hello " + username + ",\n\n" +
               "Welcome to Auth System! Your account has been successfully created.\n\n" +
               "You can now log in and start using our services.\n\n" +
               "Best regards,\n" +
               "Auth System Team";
    }
}