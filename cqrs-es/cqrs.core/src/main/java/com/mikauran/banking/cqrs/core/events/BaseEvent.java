package com.mikauran.banking.cqrs.core.events;

import com.mikauran.banking.cqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class BaseEvent extends Message {

    private int version;

    public BaseEvent() {
    }
}
