package com.mikauran.banking.account.common.events;

import lombok.Data;
import com.mikauran.banking.cqrs.core.events.BaseEvent;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
