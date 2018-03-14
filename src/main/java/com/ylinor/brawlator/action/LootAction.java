package com.ylinor.brawlator.action;

import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.LootTableBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import com.ylinor.itemizer.service.IItemService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class LootAction {

    public static Optional<ItemStack> getloot(String monsterName){
       Optional<LootTableBean> lootTableOpt = ConfigurationHandler.lootTableList.stream().filter(c->c.getName().equals(monsterName)).findFirst();
        if(lootTableOpt.isPresent()){
            LootTableBean lootTableBean = lootTableOpt.get();
            Optional<IItemService> optionalIItemService = Sponge.getServiceManager().provide(IItemService.class);
            if (optionalIItemService.isPresent()) {
                IItemService iItemService = optionalIItemService.get();
                if(lootTableBean.getItem().isPresent()){
                   return iItemService.retrieve(lootTableBean.getItem().get());

                } else if(lootTableBean.getPool().isPresent()){
                    return iItemService.fetch(lootTableBean.getPool().get());
                }
            }
            if (lootTableBean.getItemname().isPresent()){
              Optional<ItemType> itemTypeOptional = Sponge.getRegistry().getType(ItemType.class,lootTableBean.getItemname().get());
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
