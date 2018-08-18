package com.onaple.brawlator.action;

import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.LootTableBean;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import com.onaple.itemizer.service.IItemService;
import org.slf4j.Logger;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Singleton
public class LootAction {
    @Inject
    private Logger logger;

    @Inject
    private Brawlator brawlator;

    @Inject
    private ConfigurationHandler configurationHandler;

    /**
     * Get the loot from a monster name
     *
     * @param monsterName monster that you want to get the lootTable
     * @return the corresponding ItemStack
     */
    public List<ItemStack> getloot(String monsterName) {
        Optional<LootTableBean> lootTableOpt = configurationHandler.getLootTableList().stream().filter(c -> c.getName().equals(monsterName)).findFirst();
        List<ItemStack> loots = new ArrayList<>();
        if (lootTableOpt.isPresent()) {
            LootTableBean lootTableBean = lootTableOpt.get();
            if (lootTableBean.getPool() > 0) {
                Optional<IItemService> itemService = brawlator.getItemService();
                if (itemService.isPresent()) {
                    Optional<ItemStack> fetchedItem = itemService.get().fetch(lootTableBean.getPool());
                    fetchedItem.ifPresent(loots::add);
                } else {
                    logger.error("You must include Itemizer plugin if you want to use item pools");
                }
            }
            loots.addAll(lootTableBean.getItems());
        }
        logger.warn("No loot table defined");
        return loots;
    }

    /**
     * Spawn an itemstack at a given block position
     *
     * @param itemStack Item to spawn
     * @param location  Location of the block
     * @return Entity spawned
     */
    public Entity createItemEntity(ItemStack itemStack, Location<World> location) {
        location = location.add(0.5, 0.25, 0.5);
        Extent extent = location.getExtent();
        Entity itemEntity = extent.createEntity(EntityTypes.ITEM, location.getPosition());
        itemEntity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
        return itemEntity;
    }
}
