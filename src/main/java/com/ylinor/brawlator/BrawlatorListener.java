package com.ylinor.brawlator;

import com.ylinor.brawlator.action.LootAction;
import com.ylinor.itemizer.service.IItemService;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BrawlatorListener {


    @Listener
    public void onDropItemEvent(DropItemEvent.Destruct event){
        if( event.getSource() instanceof Entity) {
            event.getEntities().clear();
            Optional<Entity> entityOptional = event.getCause().first(Entity.class);
            if (entityOptional.isPresent()) {
                Entity entity = entityOptional.get();
                Brawlator.getLogger().info(entity.getType().getName());
                Optional<Text> nameOptional = entity.get(Keys.DISPLAY_NAME);
                if (nameOptional.isPresent()) {
                    String name = nameOptional.get().toPlain();
                    Brawlator.getLogger().info(name);
                    Optional<ItemStack> itemStackOptional = Brawlator.getLootAction().getloot(name);
                    itemStackOptional.ifPresent(itemStack -> event.getEntities().add(cretateItemEntity(itemStack, entity.getLocation())));
                }
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
    public Entity cretateItemEntity(ItemStack itemStack, Location<World> location) {
        location = location.add(0.5, 0.25, 0.5);
        Extent extent = location.getExtent();
        Entity itemEntity = extent.createEntity(EntityTypes.ITEM, location.getPosition());
        itemEntity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
        return itemEntity;
    }
}
