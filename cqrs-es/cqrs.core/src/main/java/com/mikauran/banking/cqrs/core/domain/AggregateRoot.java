package com.mikauran.banking.cqrs.core.domain;

import com.mikauran.banking.cqrs.core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommitedChanges() {
        return this.changes;
    }

    public void markChangesAsCommit(){
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, Boolean isNewEvent){
        try{
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch (NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("The apply method is not found {0}", event.getClass().getName()));
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error presented at moment to add the Aggregate", e);
        }finally{
            if(isNewEvent){
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event){
        applyChanges(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events){
        events.forEach(event -> applyChanges(event, false));
    }
}
