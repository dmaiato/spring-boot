package com.javanauta.learningspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.learningspring.business.UserService;
import com.javanauta.learningspring.controller.dtos.UserDTO;
import com.javanauta.learningspring.infrastructure.entity.User;
import com.javanauta.learningspring.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.saveUser(user));
  }

  @PostMapping("login")
  public String login(@RequestBody UserDTO userDTO) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

    return "Bearer " + jwtUtil.generateToken(authentication.getName());
  }

  @GetMapping
  public ResponseEntity<User> searchUserByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok(userService.searchUserByEmail(email));
  }

  @DeleteMapping("/{email}")
  public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
    userService.deleteUserByEmail(email);
    return ResponseEntity.ok().build();
  }
}
