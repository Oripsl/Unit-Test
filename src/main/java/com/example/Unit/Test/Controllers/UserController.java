package com.example.Unit.Test.Controllers;

import com.example.Unit.Test.Entities.User;
import com.example.Unit.Test.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAll() {
        return userRepository.findAll().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getSingle(@PathVariable Long id) {
        return userRepository.findById(id).isPresent() ? ResponseEntity.ok().body(userRepository.findById(id).get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id).map(foundUser -> {
            foundUser.setBirthDate(user.getBirthDate());
            foundUser.setEmail(user.getEmail());
            foundUser.setFullName(user.getFullName());
            foundUser.setPhoneNumber(user.getPhoneNumber());
            return ResponseEntity.ok().body(userRepository.save(foundUser));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
