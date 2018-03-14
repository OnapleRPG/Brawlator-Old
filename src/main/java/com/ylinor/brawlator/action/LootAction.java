package com.ylinor.brawlator.action;

import com.ylinor.brawlator.data.beans.LootTableBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import com.ylinor.itemizer.service.IItemService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class LootAction {

    public Optional<ItemStack> getloot(String monstername){
       Optional<LootTableBean> lootTableOpt = ConfigurationHandler.lootTableList.stream().filter(c->c.getName().equals(monstername)).findFirst();
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
        }
        return Optional.empty();
    }
}
