package com.main.catchy.controller;


import com.main.catchy.model.RefreshToken;
import com.main.catchy.security.jwt.JwtUtils;
import com.main.catchy.security.services.RefreshTokenService;
import com.main.catchy.security.services.UserDetailsImpl;
import com.main.catchy.services.UserServicesImp;
import com.main.catchy.utils.JwtResponce.JwtResponse;
import com.main.catchy.utils.JwtResponce.TokenRefreshException;
import com.main.catchy.utils.JwtResponce.TokenRefreshRequest;
import com.main.catchy.utils.JwtResponce.TokenRefreshResponse;
import com.main.catchy.utils.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserServicesImp userservices;
  @Autowired
  RefreshTokenService refreshTokenService;
  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt,
                          refreshToken.getToken(),
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles,refreshToken.getExpiryDate()));
  }
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              String token = jwtUtils.generateTokenFromUsername(user.getUsername());
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not in database!"));
  }
  @PostMapping("/logoutUser")
  public void logout(@RequestBody long id) {
    userservices.addlogoutActivity(id);
  }

}
