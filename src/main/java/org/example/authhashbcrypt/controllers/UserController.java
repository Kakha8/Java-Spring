package org.example.authhashbcrypt.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.authhashbcrypt.common.HashPassword;
import org.example.authhashbcrypt.entity.User;
import org.example.authhashbcrypt.repository.PasswordRepository;
import org.example.authhashbcrypt.repository.RegisterRepository;
import org.example.authhashbcrypt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RegisterRepository registerRepo;

    @Autowired
    private PasswordRepository passwordRepo;

    // Display login form
    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        System.out.println("salt: " + passwordRepo.getSaltFromUser("kakha"));

        if (session.getAttribute("user") != null) {

            //ugghh
            return "welcome"; // Redirect to welcome page if user session exists
        }else return "login";
    }

    // Handle login submission
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model, HttpSession session) {


        User auth = authenticateUsername(username);


        if (auth != null) {
            String hashedPassword = auth.getPassWord();

            boolean verifyPassword = HashPassword.verifyPassword(password, hashedPassword);

            if(verifyPassword){
                // Store the user in the session
                session.setAttribute("user", username); // Storing the authenticated user

                List<User> retrivedUsers = listUsers();
                model.addAttribute("user", listUsers().get(0).getUserName());


                model.addAttribute("retrivedUsers", retrivedUsers);
                // Pass only the username to the welcome page
                model.addAttribute("username", username);



                System.out.println(listUsers());
                return "welcome";
            } else {
                model.addAttribute("failed", "wrong!!");
                return "login";
            }

        } else {
            model.addAttribute("failed", "wrong!!");
            return "login";
        }


    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String retype,
                                  Model model){


        boolean match = registerRepo.passwordsMatch(password, retype);

        if(match){

            String hashedPassword = HashPassword.hashPassword(password);
            String salt = HashPassword.getSaltFromHash(hashedPassword);

            User user = new User(username, email, hashedPassword, salt);

            registerRepo.registerUser(user);

            System.out.println("Hashed password: " + hashedPassword);
            System.out.println("Salt: " + salt);
            return "login";
        } else model.addAttribute("failed", "passwords don't match!");

        return null;
        //userRepo.registerUser(user);

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }

    private User authenticateUsername(String username) {
        return userRepo.authenticateUser(username);
    }

    private List<User> listUsers(){
        return userRepo.listUsers();
    }

}
