package com.investmentBA.investmentBanking.controller;

import com.investmentBA.investmentBanking.model.Userr;
import com.investmentBA.investmentBanking.repository.UserRepository;
import com.investmentBA.investmentBanking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Userr user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody Userr user) {
        Userr existing = userRepo.findByUsername(user.getUsername());
        if (existing != null && encoder.matches(user.getPassword(), existing.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        return "Invalid credentials";
    }
}
