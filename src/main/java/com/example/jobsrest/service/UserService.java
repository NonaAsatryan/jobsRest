package com.example.jobsrest.service;

import com.example.jobsrest.entity.User;
import com.example.jobsrest.entity.UserType;
import com.example.jobsrest.exception.ResourceNotFoundException;
import com.example.jobsrest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${images.upload.path}")
    public String imagePath;
    @Value("${files.upload.path}")
    public String filePath;

    public User save(User user) {
        if (user.getUserType() == UserType.EMPLOYER) {
            user.setUserType(UserType.EMPLOYER);
        } else {
            user.setUserType(UserType.USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User deleteById(int id) {
        userRepository.deleteById(id);
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User edit(User userFromDb) {
        return userRepository.save(userFromDb);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getById(int id)
    {
        return userRepository.getById(id);
    }

    public  User findById(int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new ResourceNotFoundException("User does not found" + byId.get().getName());
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
