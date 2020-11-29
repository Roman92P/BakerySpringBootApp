package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.email.MailSender;
import com.bakery.shark.bakery_shark.app.user.CurrentUser;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class BillController {

    private final Logger logger = LoggerFactory.getLogger(BillController.class);

    private final MailSender mailSender;

    private final JavaMailSender javaMailSender;


    @RequestMapping(value = "/cashRegister/bill/complete", method = RequestMethod.POST)
    public String reciveCompleteBill(@RequestParam String srcImg, @AuthenticationPrincipal CurrentUser currentUser) {



        String[] arr = srcImg.split(",");
        String targetImgSrc=arr[1];

        String imageDataBytes = srcImg.substring(srcImg.indexOf(",")+1);
        logger.error("reciver img: " + imageDataBytes);


        byte[] decodedBytes = Base64.getDecoder().decode(imageDataBytes);
        try {
            FileUtils.writeByteArrayToFile(new File("bill.png"), decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("forcodeemailroman@gmail.com");
            helper.setTo("forcodeemailroman@gmail.com");
            helper.setSubject("New bill" + LocalDateTime.now());
            helper.setText("", true);
            helper.addAttachment("bill", new File("bill.png"));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }




//        mailSender.send(message);

        return "redirect:/cashRegister";
    }
}
