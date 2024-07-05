package com.mikauran.banking.account.cmd.api.command;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DepositFundsCommand extends BaseCommand {
    private BigDecimal amount;


}
