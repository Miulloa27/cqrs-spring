package com.mikauran.banking.account.cmd.infrastructure;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;
import com.mikauran.banking.cqrs.core.commands.CommandHandlerMethod;
import com.mikauran.banking.cqrs.core.infraestructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, C -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());

        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("The command handler has not been register");
        }

        if(handlers.size() > 1 ){
            throw new RuntimeException("You can't send a command that have more than one handler");
        }

        handlers.get(0).handle(command);
    }
}
