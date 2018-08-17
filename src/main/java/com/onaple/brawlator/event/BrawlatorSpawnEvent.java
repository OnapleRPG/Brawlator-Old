package com.onaple.brawlator.event;

import com.onaple.brawlator.data.beans.SpawnerBean;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * Event rised when a time of a Spawner come to 0
 */
public class BrawlatorSpawnEvent extends AbstractEvent {

    private final SpawnerBean spawnerBean;

    public BrawlatorSpawnEvent(SpawnerBean spawnerBean) {
        this.spawnerBean = spawnerBean;
    }

    public SpawnerBean getSpawnerBean() {
        return spawnerBean;
    }

    @Override
    public Cause getCause() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public EventContext getContext() {
        return null;
    }
}
