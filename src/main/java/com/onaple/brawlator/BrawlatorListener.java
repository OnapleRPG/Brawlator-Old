package com.onaple.brawlator;

import com.onaple.brawlator.action.LootAction;
import org.slf4j.Logger;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import java.util.Optional;

public class BrawlatorListener {
    @Inject
    private Logger logger;

    @Inject
    private LootAction lootAction;

    @Listener
    public void onDropItemEvent(DropItemEvent.Destruct event) {
        event.getEntities().clear();
        Optional<Entity> entityOptional = event.getCause().first(Entity.class);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            Optional<Text> nameOptional = entity.get(Keys.DISPLAY_NAME);
            if (nameOptional.isPresent()) {
                String name = nameOptional.get().toPlain();
                lootAction.getloot(name).forEach(itemStack -> event.getEntities()
                        .add(lootAction.createItemEntity(itemStack, entity.getLocation())));
            }
        }
    }
}
