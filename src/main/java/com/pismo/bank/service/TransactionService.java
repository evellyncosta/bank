package com.pismo.bank.service;

import com.pismo.bank.exceptions.BusinessException;
import com.pismo.bank.model.Account;
import com.pismo.bank.model.Transaction;
import com.pismo.bank.model.enums.OperationType;
import com.pismo.bank.repository.AccountRepository;
import com.pismo.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction save(Transaction transaction) throws BusinessException {
        Optional<Account> account = accountRepository.findById(transaction.getAccount().getId());
        if(account.isEmpty()){
            throw  new BusinessException("Account does not exist");
        }
        Transaction transactionValidated = adjustTransactions(transaction);
        Transaction transactionSaved = transactionRepository.save(transactionValidated);
        return transactionSaved;
    }

    private Transaction adjustTransactions(Transaction transaction){
        if(transaction.getOperationTypeId().equals(OperationType.COMPRA_A_VISTA) || transaction.getOperationTypeId().equals(OperationType.COMPRA_PARCELADA) || transaction.getOperationTypeId().equals(OperationType.SAQUE)){
            return adjustNegativeTransactions(transaction);
        }

        return adjustPositiveTransactions(transaction);
    }

    //validar 0 e 0.0
    private Transaction adjustNegativeTransactions(Transaction transaction){
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) > 0){
            transaction.setAmount(transaction.getAmount().negate());
        }

        return transaction;
    }

    private Transaction adjustPositiveTransactions(Transaction transaction){
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0){
            transaction.setAmount(transaction.getAmount().negate());
        }

        return transaction;
    }
}
