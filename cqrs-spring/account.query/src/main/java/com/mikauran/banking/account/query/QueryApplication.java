package com.mikauran.banking.account.query;

import com.mikauran.banking.account.query.api.queries.*;
import com.mikauran.banking.cqrs.core.infraestructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {

	private final QueryDispatcher queryDispatcher;
	private final QueryHandler queryHandler;

	@Autowired
	public QueryApplication(QueryDispatcher queryDispatcher, QueryHandler queryHandler) {
		this.queryDispatcher = queryDispatcher;
		this.queryHandler = queryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		queryDispatcher.registerHandler(FindAllAccountQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handle);
	}

}
