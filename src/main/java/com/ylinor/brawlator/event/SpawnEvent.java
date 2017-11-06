package com.ylinor.brawlator.event;

import com.ylinor.brawlator.data.beans.SpawnerBean;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class SpawnEvent extends AbstractEvent {
    private final Cause cause;
    private final SpawnerBean spawnerBean;

    public SpawnEvent(Cause cause, SpawnerBean spawnerBean) {
        this.cause = cause;
        this.spawnerBean = spawnerBean;
    }

    @Override
    public Cause getCause() {
        return this.cause;
    }

    public SpawnerBean getSpawnerBean() {
        return spawnerBean;
    }
}
