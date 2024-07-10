package com.mikauran.banking.account.cmd.infrastructure;

import com.mikauran.banking.cqrs.core.events.BaseEvent;
import com.mikauran.banking.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer{
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    //Esta parte se puede mover a un bean de configuracion
    //para poder tener todos los elementos desde el inicio
    @Override
    public void produce(String topic, BaseEvent event){
        this.kafkaTemplate.send(topic, event);
    }
}
