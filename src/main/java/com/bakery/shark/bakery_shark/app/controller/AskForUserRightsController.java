package com.bakery.shark.bakery_shark.app.controller;

import com.bakery.shark.bakery_shark.app.email.MailSender;
import com.bakery.shark.bakery_shark.app.model.Role;
import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.role.RoleRepository;
import com.bakery.shark.bakery_shark.app.user.CurrentUser;
import com.bakery.shark.bakery_shark.app.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AskForUserRightsController {
    @Autowired
    private final MailSender mailSender;

    private final UserService userService;
    private final RoleRepository roleRepository;

    @RequestMapping("/user/askForUserRights")
    public String askForUserRights(@AuthenticationPrincipal CurrentUser currentUser){
        String message = String.format("Dear Admin! \n User: %s \n Id: %d\n is asking for user rights.\n If you accept - click link bellow.\n" +
                        "http://localhost:8080/admin/addUserRights/%d" +
                        "\nBest Regards,\n Development Team",
                currentUser.getUsername(),
                currentUser.getUser().getId(),
                currentUser.getUser().getId());

        mailSender.send("forcodeemailroman@gmail.com", "User Role request"+ LocalDateTime.now(), ""+message);

        return "redirect:/";
    }

    @RequestMapping("/admin/addUserRights/{id}")
    public String giveUserAdminRights(@PathVariable Long id){
        User user = userService.findByUserId(id).orElseThrow(EntityNotFoundException::new);
        User updated = new User();
        updated.setId(user.getId());
        updated.setActive(user.isActive());
        updated.setEmail(user.getEmail());
        updated.setEnabled(user.isEnabled());
        updated.setFirstName(user.getFirstName());
        updated.setLastName(user.getLastName());
        updated.setPassword(user.getPassword());
        updated.setUsername(user.getUsername());
        Role userRole = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = user.getRoles();
        roles.add(userRole);
        updated.setRoles(roles);
        userService.updateUser(updated);
        return "redirect:/main";
    }
}
