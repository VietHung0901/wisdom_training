package com.Wisdom_Training.controller;

import com.Wisdom_Training.config.JwtService;
import com.Wisdom_Training.dto.request.AccountDTO;
import com.Wisdom_Training.dto.request.LoginDTO;
import com.Wisdom_Training.entity.Account;
import com.Wisdom_Training.service.impl.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final JwtService jwtService;

    @PostMapping()
    public ResponseEntity<?> Register(@RequestBody AccountDTO accountDTO) {
        try {
            accountService.save(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo account thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi tạo category: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            // Nếu xác thực thành công, tạo token và trả về

            // Nếu xác thực thành công
            if (authentication.isAuthenticated()) {
                // Lấy thông tin Account từ đối tượng xác thực
                Account account = (Account) authentication.getPrincipal();

                // Tạo JWT token
                String token = jwtService.generateToken(account.getUsername());

                Account account1 = accountService.getAccountByUsername(loginDTO.getUsername());

                // Trả về token và role
                // Trả về token và role
                return ResponseEntity.ok(Map.of(
                        "message", "Login successfully!",
                        "data", Map.of(
                                "token", "Bearer " + token,
                                "username", loginDTO.getUsername(),
                                "role", account1.getRole()
                        )
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (LockedException | DisabledException | AccountExpiredException | CredentialsExpiredException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
        }
    }
}