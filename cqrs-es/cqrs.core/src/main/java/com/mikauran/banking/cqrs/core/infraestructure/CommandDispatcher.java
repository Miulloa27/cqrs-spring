package com.mikauran.banking.cqrs.core.infraestructure;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;
import com.mikauran.banking.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class <T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
