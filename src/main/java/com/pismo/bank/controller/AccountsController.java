package com.pismo.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pismo.bank.exceptions.BusinessException;
import com.pismo.bank.model.Account;
import com.pismo.bank.model.Transaction;
import com.pismo.bank.repository.AccountRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/accounts")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountsController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired private ObjectMapper objectMapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAll(){
        List<Account> accounts = accountRepository.findAll();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        Optional<Account> accountFromDatabase = accountRepository.findById(id);
        if (accountFromDatabase.isPresent()){
            return ResponseEntity.ok().body(accountFromDatabase.get());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    @PostMapping
    public ResponseEntity<?> saveAccount(@RequestBody  @Valid Account account) throws BusinessException {
        Optional<Account> existentAccount = accountRepository.findAccountByDocumentNumber(account.getDocumentNumber());
       if (existentAccount.isPresent()){
           throw new BusinessException("this document number is already used");
       }

        account.setId(null);
        Account persistedAccount = accountRepository.save(account);
        return ResponseEntity
                .created(URI
                        .create(String.format("/accounts/%s", persistedAccount.getId())))
                .body(persistedAccount);


    }


}
