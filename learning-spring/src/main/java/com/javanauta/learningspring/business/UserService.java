package com.javanauta.learningspring.business;

import org.springframework.stereotype.Service;

import com.javanauta.learningspring.infrastructure.entity.User;
import com.javanauta.learningspring.infrastructure.exceptions.ConflictException;
import com.javanauta.learningspring.infrastructure.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  // @Autowired // é usado para injetar dependências automaticamente
  private final UserRepository userRepository;

  public User saveUser(User user) {
    try {
      emailExists(user.getEmail());
      return userRepository.save(user);
    } catch (ConflictException e) {
      throw new ConflictException("Email already registered", e.getCause());
    }
  }

  public void emailExists(String email) {
    try {
      boolean exists = verifyExistingEmail(email);
      if (exists) {
        throw new ConflictException("Email already registered: " + email);
      }
    } catch (ConflictException e) {
      throw new ConflictException("Email already registered", e.getCause());
    }
  }

  public boolean verifyExistingEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
