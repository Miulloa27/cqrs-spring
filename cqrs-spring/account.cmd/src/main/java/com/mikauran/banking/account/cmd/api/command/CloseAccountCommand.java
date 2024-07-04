package com.mikauran.banking.account.cmd.api.command;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }
}
