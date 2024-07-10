package com.mikauran.banking.account.cmd;

import com.mikauran.banking.account.cmd.api.command.*;
import com.mikauran.banking.cqrs.core.infraestructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {

	private final CommandDispatcher commandDispatcher;
	private final CommandHandler commandHandler;

	@Autowired
	public CommandApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
		this.commandDispatcher = commandDispatcher;
		this.commandHandler = commandHandler;
	}

	public static void main(String[] args) {

		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}

}
