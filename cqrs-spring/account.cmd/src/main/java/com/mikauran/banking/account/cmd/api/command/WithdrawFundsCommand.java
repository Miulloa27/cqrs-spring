package com.mikauran.banking.account.cmd.api.command;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
