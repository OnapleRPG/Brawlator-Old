package com.onaple.brawlator.action;

import com.onaple.brawlator.data.beans.LootTableBean;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import com.onaple.brawlator.Brawlator;
import com.onaple.itemizer.service.IItemService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Singleton
public class LootAction {

    @Inject
    private ConfigurationHandler configurationHandler;

    public LootAction() {}

    /**
     * Get the loot from a monster name
     * @param monsterName monster that you want to get the lootTable
     * @return the corresponding ItemStack
     */
    public List<ItemStack> getloot(String monsterName){
       Optional<LootTableBean> lootTableOpt = configurationHandler.getLootTableList().stream().filter(c->c.getName().equals(monsterName)).findFirst();
       List<ItemStack> loots = new ArrayList<>();
       if(lootTableOpt.isPresent()){
            LootTableBean lootTableBean = lootTableOpt.get();


            if(lootTableBean.getPool()>0){
                if( Brawlator.getItemService().isPresent()){
                    Optional<ItemStack> fetchedItem = Brawlator.getItemService().get().fetch(lootTableBean.getPool());
                    fetchedItem.ifPresent(loots::add);
                } else {
                    Brawlator.getLogger().error("You must include Itemizer plugin if you want to you item's pool");
                }
            }
            loots.addAll(lootTableBean.getItems());
        }
        Brawlator.getLogger().warn("No loot table defined");
        return loots;
    }
}
