package com.pismo.bank.service;

import com.pismo.bank.exceptions.BusinessException;
import com.pismo.bank.model.Account;
import com.pismo.bank.model.Transaction;
import com.pismo.bank.model.enums.OperationType;
import com.pismo.bank.repository.AccountRepository;
import com.pismo.bank.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)

@SpringBootTest
@ActiveProfiles("test")
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Dado uma transacao do tipo COMPRA_A_VISTA, o valor da transacao salva no banco deve ser negativo")
    public void testSaveTransactionCompraAvistaValorPositivo() throws BusinessException {
        Transaction transactionInput = transactionBuilder(OperationType.COMPRA_A_VISTA, new BigDecimal(200));
        Transaction result = transactionService.save(transactionInput);
        assertNotNull(result);
        Assert.assertTrue(isNegativeNumber(result.getAmount()));
    }

    @Test
    @DisplayName("Dado uma transacao do tipo COMPRA_PARCELADA, o valor da transacao salva no banco deve ser negativo")
    public void testSaveTransactionCompraParceladaValorPositivo() throws BusinessException {
        Transaction transactionInput = transactionBuilder(OperationType.COMPRA_PARCELADA, new BigDecimal(200));

        Transaction result = transactionService.save(transactionInput);
        assertNotNull(result);
        Assert.assertTrue(isNegativeNumber(result.getAmount()));
    }

    @Test
    @DisplayName("Dado uma transacao do tipo SAQUE, o valor da transacao salva no banco deve ser negativo")
    public void testSaveTransactionCompraSaque() throws BusinessException {
        Transaction transactionInput = transactionBuilder(OperationType.SAQUE, new BigDecimal(150));

        Transaction result = transactionService.save(transactionInput);
        assertNotNull(result);
        Assert.assertTrue(isNegativeNumber(result.getAmount()));
    }

    @Test
    @DisplayName("Dado uma transacao do tipo PAGAMENTO, o valor da transacao salva no banco deve ser positivo")
    public void testSaveTransactionPagamento() throws BusinessException {
        Transaction transactionInput = transactionBuilder(OperationType.PAGAMENTO, new BigDecimal(-150));

        Transaction result = transactionService.save(transactionInput);
        assertNotNull(result);
        Assert.assertTrue(!isNegativeNumber(result.getAmount()));
    }



    private boolean isNegativeNumber(BigDecimal number){
        return (number.compareTo(BigDecimal.ZERO)==-1) ? true : false;
    }

    private Transaction transactionBuilder(OperationType type, BigDecimal amount){
        Random ran = new Random();

        Account account = new Account();

        account.setDocumentNumber(String.valueOf(ran.nextInt()));
        Account accountSaved = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setId(ran.nextLong());
        transaction.setAccount(accountSaved);

        transaction.setAmount(amount);
        transaction.setOperationTypeId(type);
        return transaction;
    }
}