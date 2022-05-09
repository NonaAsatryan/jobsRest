package com.example.jobsrest.controller;

import com.example.jobsrest.dto.*;
import com.example.jobsrest.entity.User;
import com.example.jobsrest.entity.UserType;
import com.example.jobsrest.security.CurrentUser;
import com.example.jobsrest.service.MailService;
import com.example.jobsrest.service.UserService;
import com.example.jobsrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    private final MailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;

    @PostMapping("/users")
    public ResponseEntity saveUser(@RequestBody User saveUserRequest, @RequestParam("picture") MultipartFile file) throws IOException {
      if(userService.findByEmail(saveUserRequest.getEmail()) != null){
          return ResponseEntity
                  .status(HttpStatus.CONFLICT)
                  .build();
      }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imagePath + File.separator + fileName);
        file.transferTo(picture);
        saveUserRequest.setPicUrl(fileName);
        saveUserRequest.setPassword(passwordEncoder.encode(saveUserRequest.getPassword()));
        saveUserRequest.setToken(UUID.randomUUID().toString());
        userService.save(saveUserRequest);

        String link = "http://localhost:8080/activate/{email}" + saveUserRequest.getEmail()
                + "/{token}" + saveUserRequest.getToken();
        emailService.sendMail(saveUserRequest.getEmail(), "Welcome ", "Dear  " + saveUserRequest.getName() + "  you have successfully registered.Please activate your account by clicking on link  " + link);
        return ResponseEntity.ok(saveUserRequest);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserLoginRequest userLoginRequest) {
     String email = userLoginRequest.getEmail();
     User byEmail = userService.findByEmail(email);

     if(byEmail != null){
         User user = byEmail;
         if(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){
             String token = jwtTokenUtil.generateToken(user.getEmail());
             UserLoginResponse responseDto = UserLoginResponse.builder()
                     .token(token)
                     .user(user)
                     .build();
             return ResponseEntity.ok(responseDto);
         }
     }
     return ResponseEntity
             .status(HttpStatus.UNAUTHORIZED)
             .build();

    }

    @GetMapping("/activate/{email}/{token}")
    public ResponseEntity activate(@PathVariable String email, @PathVariable String token){
        User byEmail = userService.findByEmail(email);
        if(byEmail != null){
            User user = byEmail;
            if(user.getToken().equals(token)){
                user.setActive(true);
                user.setToken("");
                userService.save(user);
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }
    @PostMapping("/users")
    public ResponseEntity saveEmployer(@RequestBody User saveUserRequest, @RequestParam("picture") MultipartFile file) throws IOException {
        if(userService.findByEmail(saveUserRequest.getEmail()) != null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imagePath + File.separator + fileName);
        file.transferTo(picture);
        saveUserRequest.setPicUrl(fileName);
        saveUserRequest.setPassword(passwordEncoder.encode(saveUserRequest.getPassword()));
        saveUserRequest.setToken(UUID.randomUUID().toString());
        userService.save(saveUserRequest);

        String link = "http://localhost:8080/activate/{email}" + saveUserRequest.getEmail()
                + "/{token}" + saveUserRequest.getToken();
        emailService.sendMail(saveUserRequest.getEmail(), "Welcome ", "Dear  " + saveUserRequest.getName() + "  you have successfully registered.Please activate your account by clicking on link  " + link);
        return ResponseEntity.ok(saveUserRequest);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
      User byId =  userService.getById(id);
      if(byId != null ){
          userService.deleteById(id);
          return ResponseEntity
                  .ok()
                  .build();
      }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/user/account")
    public ResponseEntity userAccount(@AuthenticationPrincipal CurrentUser springUser) {
        return ResponseEntity
                .ok(springUser.getUser());

    }

    @PostMapping("/user/updatePassword")
    public ResponseEntity updatePassword(@RequestBody Map<String, String> password,
                                         @AuthenticationPrincipal CurrentUser currentUser) {

        User one = userService.findById(currentUser.getUser().getId());
        if (one == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        if (passwordEncoder.matches(password.get("oldPassword"),one.getPassword())) {
            one.setPassword(passwordEncoder.encode(password.get("newPassword")));
            userService.save(one);
        }

        return ResponseEntity
                .ok(one);
    }

    @GetMapping("/employer/account")
    public ResponseEntity employerAccount(@AuthenticationPrincipal CurrentUser springEmployer) {
        return ResponseEntity
                .ok(springEmployer.getUser());

    }

    @GetMapping("/user/profileDetails")
    public ResponseEntity profileDetailsPage(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity
                .ok(currentUser.getUser());    }

    @PostMapping("/user/update")
    public ResponseEntity updateUserProfile(@RequestBody Map<String, String> profile,
                                    @AuthenticationPrincipal User user, CurrentUser currentUser) {
        if (user.getUserType() == UserType.ADMIN) {
            return ResponseEntity
                    .ok(currentUser.getUser());            }
            if (user.getUserType() == UserType.EMPLOYER) {
                return ResponseEntity
                        .ok(currentUser.getUser());
            } else {
                return ResponseEntity
                        .ok(currentUser.getUser());
            }
    }
    @GetMapping("/admin/getUserById/{id}")
    public ResponseEntity findUserById(@PathVariable("id") int id) {
        User byId = userService.findById(id);
        if (byId != null) {
            return ResponseEntity.ok(byId);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/user/edit/{id}")
    public ResponseEntity editProfileDetails(@PathVariable("id") int id) {
        User byId = userService.findById(id);
        if (byId != null) {
            userService.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/admin/userDelete/{id}")
    public ResponseEntity deleteById(@PathVariable("id") int id) {
        User byId = userService.findById(id);
        if (byId != null) {
            userService.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
}