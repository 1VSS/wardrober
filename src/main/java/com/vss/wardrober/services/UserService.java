package com.vss.wardrober.services;

import com.vss.wardrober.models.UserModel;
import com.vss.wardrober.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserModel> findByUsername(@NotBlank(message = "Username is required") String username) {
        return userRepository.findByUsername(username);
    }
}
