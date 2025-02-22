package com.ust.Security.service;

import com.ust.Security.dto.ForgotPasswordDTO;
import com.ust.Security.dto.ResetPasswordDTO;
import com.ust.Security.model.Userinfo;
import com.ust.Security.repository.Userinforepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class Userservices {
    @Autowired
    private Userinforepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(Userinfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "User added to system";
    }

    public String forgotPassword(ForgotPasswordDTO request) {
        Optional<Userinfo> userOptional = repo.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return "User with email not found";
        }
        Userinfo user = userOptional.get();
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        repo.save(user);
        return "Reset Token: " + resetToken; // In real-world, send via email
    }

    public String resetPassword(ResetPasswordDTO request) {
        Optional<Userinfo> userOptional = repo.findByResetToken(request.getToken());
        if (userOptional.isEmpty()) {
            return "Invalid or expired reset token";
        }
        Userinfo user = userOptional.get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null); // Clear reset token after successful reset
        repo.save(user);
        return "Password successfully reset";
    }
}
