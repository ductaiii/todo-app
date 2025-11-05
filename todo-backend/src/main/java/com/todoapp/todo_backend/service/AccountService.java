package com.todoapp.todo_backend.service;

import com.todoapp.todo_backend.model.Account;
import com.todoapp.todo_backend.repository.AccountRepository;
import com.todoapp.todo_backend.dto.AccountDTO;
import com.todoapp.todo_backend.mapper.AccountMapper;
import com.todoapp.todo_backend.dto.AuthResponseDTO;
import com.todoapp.todo_backend.dto.LoginRequestDTO;
import com.todoapp.todo_backend.dto.RegisterRequestDTO;
import com.todoapp.todo_backend.util.PasswordUtil;
import com.todoapp.todo_backend.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        // Use fetch join to initialize collections before serialization
        return accountRepository.findAllWithChildren();
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> findAllDto() {
        List<Account> list = accountRepository.findAllWithChildren();
        return list.stream().map(AccountMapper::toDto).collect(Collectors.toList());
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Account> accountOptional = accountRepository.findByUsername(loginRequest.getUsername());

        if (accountOptional.isEmpty()) {
            throw new CustomException("Tên tài khoản không tồn tại", HttpStatus.BAD_REQUEST);
        }

        Account account = accountOptional.get();

        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), account.getPasswordHash())) {
            throw new CustomException("Mật khẩu không đúng", HttpStatus.BAD_REQUEST);
        }

        // Generate token (placeholder, replace with JWT if needed)
        String token = "dummy-token-for-" + account.getUsername();

        return new AuthResponseDTO(token, account.getUsername(), "Đăng nhập thành công");
    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        if (accountRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new CustomException("Tên tài khoản đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        if (accountRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new CustomException("Email đã được sử dụng", HttpStatus.BAD_REQUEST);
        }

        Account newAccount = new Account();
        newAccount.setUsername(registerRequest.getUsername());
        newAccount.setEmail(registerRequest.getEmail());
        newAccount.setPasswordHash(PasswordUtil.hashPassword(registerRequest.getPassword()));

        accountRepository.save(newAccount);

        // Generate token (placeholder, replace with JWT if needed)
        String token = "dummy-token-for-" + newAccount.getUsername();
        return new AuthResponseDTO(token, newAccount.getUsername(), "Đăng ký thành công");
    }
}
