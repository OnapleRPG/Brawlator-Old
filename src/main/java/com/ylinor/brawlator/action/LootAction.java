package com.ylinor.brawlator.action;

import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.LootTableBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import com.ylinor.itemizer.service.IItemService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Singleton;
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
    public Optional<ItemStack> getloot(String monsterName){
       Optional<LootTableBean> lootTableOpt = configurationHandler.lootTableList.stream().filter(c->c.getName().equals(monsterName)).findFirst();
        if(lootTableOpt.isPresent()){
            LootTableBean lootTableBean = lootTableOpt.get();
            Optional<IItemService> optionalIItemService = Sponge.getServiceManager().provide(IItemService.class);
            if (optionalIItemService.isPresent()) {
                IItemService iItemService = optionalIItemService.get();
                if(lootTableBean.getItem()>0){
                   return iItemService.retrieve(lootTableBean.getItem());

                } else if(lootTableBean.getPool()>0){
                    return iItemService.fetch(lootTableBean.getPool());
                }
            }
            if (lootTableBean.getItemname().isEmpty()){
              Optional<ItemType> itemTypeOptional = Sponge.getRegistry().getType(ItemType.class,lootTableBean.getItemname());
              if(itemTypeOptional.isPresent()){
                  return Optional.of(ItemStack.of(itemTypeOptional.get(),1));
              }
              Brawlator.getLogger().warn("No item defined");
              return Optional.empty();
            }
        }
        Brawlator.getLogger().warn("No loot table defined");
        return Optional.empty();
    }
}
