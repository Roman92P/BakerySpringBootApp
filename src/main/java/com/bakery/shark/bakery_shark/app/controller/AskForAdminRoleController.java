package com.bakery.shark.bakery_shark.app.controller;

import com.bakery.shark.bakery_shark.app.email.MailSender;
import com.bakery.shark.bakery_shark.app.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;

@Controller
public class AskForAdminRoleController {

    @Autowired
    private MailSender mailSender;

    @RequestMapping("/admin/askForRights")
    public String askForAdminRole(@AuthenticationPrincipal CurrentUser currentUser){

        mailSender.send("forcodeemailroman@gmail.com", "Admin Role request"+ LocalTime.now(), "" +
                "Dear Admin! \n User: " + currentUser.getUsername()+" \n Id: "+currentUser.getUser().getId() +
                "\n is asking for admin rights.\n If you accept - click link bellow.\nBest Regards,\n Development Team");

        return "redirect:/";
    }
}
