package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.model.Account;
import com.exe201.opalwed.service.OpalMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OpalMailServiceImpl implements OpalMailService {

    private final JavaMailSender mailSender;


    @Override
    public void sendOTP(Account account) throws MessagingException {
        // Create a MimeMessage instead of SimpleMailMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Set the email properties
        helper.setFrom("OpalWed");
        helper.setTo(account.getEmail());
        helper.setSubject("Chứng thực tài khoản");

        // HTML template for the email body
        String content = "<html>"
                + "<body>"
                + "<div style='font-family: Arial, sans-serif; text-align: center; padding: 20px;'>"
                + "<img src='cid:logoImage' style='width: 100px; height: 100px;' alt='OpalWed Logo'/>"
                + "<h2 style='color: #4CAF50;'>Chào bạn, " + account.getEmail() + "!</h2>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản tại OpalWed.</p>"
                + "<p>Mã OTP của bạn là:</p>"
                + "<h3 style='background-color: #f0f0f0; display: inline-block; padding: 10px; border-radius: 5px;'>"
                + account.getOtp().getOtpCode() + "</h3>"
                + "<p>Mã OTP này có hiệu lực trong 10 phút.</p>"
                + "<p>Nếu bạn không yêu cầu mã OTP này, vui lòng bỏ qua email này.</p>"
                + "<br><br>"
                + "<p>Trân trọng,</p>"
                + "<p>Đội ngũ hỗ trợ OpalWed</p>"
                + "</div>"
                + "</body>"
                + "</html>";

        // Set the HTML content of the email
        helper.setText(content, true);

        // Attach the logo (assuming it's in the classpath as 'static/images/logo.png')
        ClassPathResource logo = new ClassPathResource("images/opalwed-logo.png");
        helper.addInline("logoImage", logo);

        // Send the email
        mailSender.send(message);
    }
}
