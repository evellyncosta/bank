package com.pismo.bank.controller;

import com.pismo.bank.dto.TransactionData;
import com.pismo.bank.exceptions.BusinessException;
import com.pismo.bank.model.Account;
import com.pismo.bank.model.Transaction;
import com.pismo.bank.model.enums.OperationType;
import com.pismo.bank.repository.AccountRepository;
import com.pismo.bank.repository.TransactionRepository;
import com.pismo.bank.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getTransactionByID(@PathVariable String id){
        Optional<Transaction> transactionFromDatabase = transactionRepository.findById(Long.valueOf(Integer.valueOf(id)));
        if (transactionFromDatabase.isPresent()){
            return ResponseEntity.ok().body(transactionFromDatabase.get());
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Transaction updatedTransaction, @PathVariable Long id) {
        Optional<Transaction> transactionFromDatabase = transactionRepository.findById(id);

        if (transactionFromDatabase.isPresent()){
            transactionFromDatabase.map(transaction -> {
                transaction.setOperationTypeId(updatedTransaction.getOperationTypeId());
                transaction.setAmount(updatedTransaction.getAmount());
                return transactionRepository.save(transaction);
            });
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping
    public Transaction saveTransaction(@RequestBody TransactionData transactionData) throws BusinessException {
        Optional<Account> existentAccount = accountRepository.findById(transactionData.account_id());
        if (!existentAccount.isPresent()){
            throw new BusinessException("account does not exist");
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(existentAccount.get());
        transaction.setAmount(transactionData.amount());
        transaction.setOperationTypeId(OperationType.fromId(transactionData.operation_type_id()));

        return transactionService.save(transaction);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAll(){
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseEntity.ok().body(transactions);
    }

}
