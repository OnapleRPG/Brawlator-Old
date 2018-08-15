package com.onaple.brawlator;

import com.onaple.brawlator.action.LootAction;
import org.slf4j.Logger;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import javax.inject.Inject;
import java.util.Optional;

public class BrawlatorListener {
    @Inject
    private Logger logger;

    @Inject
    private LootAction lootAction;

    @Listener
    public void onDropItemEvent(DropItemEvent.Destruct event){
        logger.info(event.getSource().toString());
        event.getEntities().clear();
        Optional<Entity> entityOptional = event.getCause().first(Entity.class);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            logger.info(entity.getType().getName());
            Optional<Text> nameOptional = entity.get(Keys.DISPLAY_NAME);
            if (nameOptional.isPresent()) {
                String name = nameOptional.get().toPlain();
                logger.info(name);
                lootAction.getloot(name).forEach(itemStack -> event.getEntities()
                        .add(createItemEntity(itemStack, entity.getLocation())));
            }
        }
    }

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event){

    }

    /**
     * Spawn an itemstack at a given block position
     * @param itemStack Item to spawn
     * @param location Location of the block
     */
    public static void spawnItemStack(ItemStack itemStack, Location<World> location) {
        location = location.add(0.5, 0.25, 0.5);
        Extent extent = location.getExtent();
        Entity itemEntity = extent.createEntity(EntityTypes.ITEM, location.getPosition());
        itemEntity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
        extent.spawnEntity(itemEntity);
    }

    /**
     * Spawn an itemstack at a given block position
     * @param itemStack Item to spawn
     * @param location Location of the block
     */
    public Entity createItemEntity(ItemStack itemStack, Location<World> location) {
        location = location.add(0.5, 0.25, 0.5);
        Extent extent = location.getExtent();
        Entity itemEntity = extent.createEntity(EntityTypes.ITEM, location.getPosition());
        itemEntity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
        return itemEntity;
    }
}
