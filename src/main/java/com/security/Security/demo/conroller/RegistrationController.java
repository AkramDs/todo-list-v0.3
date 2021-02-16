package com.security.Security.demo.conroller;

import com.security.Security.demo.domain.Role;
import com.security.Security.demo.domain.User;
import com.security.Security.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String newUser(User user, Map<String, Object> model) {
        User userFromBd = userRepo.findByUsername(user.getUsername());

        if(userFromBd != null) {
            model.put("message", "Такой пользыватель уже есть!");

            return "register";

        }

        model.put("message", "Это сообщения не должно поевится НИКОГДА, если вы это видите то Антон гандон.");

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
