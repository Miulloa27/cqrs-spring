package com.mikauran.banking.account.query.api.controllers;

import com.mikauran.banking.account.query.api.dto.AccountLookupResponse;
import com.mikauran.banking.account.query.api.dto.EqualityType;
import com.mikauran.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.mikauran.banking.account.query.api.queries.FindAccountByIdQuery;
import com.mikauran.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.mikauran.banking.account.query.api.queries.FindAllAccountsQuery;
import com.mikauran.banking.account.query.domain.BankAccount;
import com.mikauran.banking.cqrs.core.infraestructure.QueryDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/v1/bankAccountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());

            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("The query was executed with sucess", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Errors appears in the queries";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path= "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value="id") String id){
        try {
            List<BankAccount> accountsById = queryDispatcher.send(new FindAccountByIdQuery(id));

            if(accountsById == null || accountsById.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .accounts(accountsById)
                    .message(MessageFormat.format("The query was executed with success", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e){
            var safeErrorMessage = "Errors appears in the queries";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path= "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value="accountHolder") String accountHolder){
        try {
            List<BankAccount> accountsByHolder = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));

            if(accountsByHolder == null || accountsByHolder.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .accounts(accountsByHolder)
                    .message(MessageFormat.format("The query was executed with success", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors appears in the queries";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path= "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
            @PathVariable(value="equalityType") EqualityType equalityType,
            @PathVariable(value = "balance")BigDecimal balance){
        try {
            List<BankAccount> accountsBalance = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));

            if(accountsBalance == null || accountsBalance.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountLookupResponse.builder()
                    .accounts(accountsBalance)
                    .message(MessageFormat.format("The query was executed with success", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Errors appears in the queries";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
