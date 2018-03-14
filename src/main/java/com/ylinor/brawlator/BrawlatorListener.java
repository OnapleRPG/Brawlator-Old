package com.ylinor.brawlator;

import com.ylinor.brawlator.action.LootAction;
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

import java.util.Optional;

public class BrawlatorListener {

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event){
       Entity entity = event.getTargetEntity();
       Optional<Text> nameOptional  = entity.get(Keys.DISPLAY_NAME);
       if(nameOptional.isPresent()){
           String name = nameOptional.get().toPlain();
           Optional<ItemStack> itemStackOptional = LootAction.getloot(name);
           if(itemStackOptional.isPresent()){
               spawnItemStack(itemStackOptional.get(),entity.getLocation());
           }
       }

    }

    /**
     * Cancel block breaking dropping event unless specified in config
     * @param event Item dropping event
     */
    @Listener
    public void onDropItemEvent(DropItemEvent.Destruct event) {
			event.setCancelled(true);
        /*List<String> defaultDrops = ConfigurationHandler.getHarvestDefaultDropList();
			Optional<Player> player = event.getCause().first(Player.class);
			if (player.isPresent()) {
				for (Entity entity: event.getEntities()) {
					Optional<ItemStackSnapshot> stack = entity.get(Keys.REPRESENTED_ITEM);
					if (stack.isPresent()) {
						if (!defaultDrops.contains(stack.get().getType().getId())) {
							event.setCancelled(true);
						}
					}
				}

		}*/
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
}
