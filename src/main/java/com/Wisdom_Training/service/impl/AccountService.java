package com.Wisdom_Training.service.impl;

import com.Wisdom_Training.dto.request.AccountDTO;
import com.Wisdom_Training.entity.Account;
import com.Wisdom_Training.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));
    }

    public void save(AccountDTO accountDTO) {
        // Kiểm tra username đã tồn tại
        if (accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username đã tồn tại.");
        }
        Account account = todo(accountDTO);
        account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
        accountRepository.save(account);
    }

    public Account todo(AccountDTO accountDTO){
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setRole(accountDTO.getRole());
        return account;
    }
}
