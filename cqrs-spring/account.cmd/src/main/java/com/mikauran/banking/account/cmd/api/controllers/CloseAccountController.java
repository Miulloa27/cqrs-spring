package com.mikauran.banking.account.cmd.api.controllers;

import com.mikauran.banking.account.cmd.api.command.CloseAccountCommand;
import com.mikauran.banking.account.cmd.api.command.WithdrawFundsCommand;
import com.mikauran.banking.account.common.dto.BaseResponse;
import com.mikauran.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.mikauran.banking.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/api/v1/close")
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;
    private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    public CloseAccountController (CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value="id") String id){
        try{
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("The account bank has been closed"), HttpStatus.OK);
        }catch(IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("The client send a request with errors {0} ", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errors appear along request processing {id}", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
