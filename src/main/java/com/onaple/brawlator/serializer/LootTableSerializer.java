package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.LootTableBean;
import com.onaple.brawlator.exception.PluginNotFoundException;
import com.onaple.itemizer.service.IItemService;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LootTableSerializer implements TypeSerializer<LootTableBean> {
    @Inject
    private Logger logger;

    @Inject
    private Brawlator brawlator;

    @Override
    public LootTableBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

        if(value.getNode("name") == null ){
            throw new ObjectMappingException("name");
        }
        LootTableBean lootTable = new LootTableBean(value.getNode("name").getString());
        if(value.getNode("pool") != null){
            lootTable.setPool(value.getNode("pool").getInt());
        }
        List<ItemStack> loots = new ArrayList<>();
        value.getNode("items").getChildrenList().forEach(o -> {
            try {
                loots.add(getItemstack(o));
            } catch (ObjectMappingException | PluginNotFoundException e) {
                logger.error(e.getMessage());
            }
        });

        lootTable.setItems(loots);
        logger.info(lootTable.toString());
        return lootTable;
    }


    public ItemStack getItemstack(ConfigurationNode value) throws ObjectMappingException, PluginNotFoundException {

        if(value.getNode("ref").getInt() != 0){
            Optional<IItemService> optionalIItemService = brawlator.getItemService();
            if (optionalIItemService.isPresent()) {
                IItemService iItemService = optionalIItemService.get();
                Optional<ItemStack> itemOptional =  iItemService.retrieve(value.getNode("ref").getInt());
                if(itemOptional.isPresent()){
                    return itemOptional.get();
                } else {
                    throw new ObjectMappingException("Wrong item references : " + value.getNode("ref").getInt()
                            + ". Check the reference or if the item exist.");
                }
            } else {
                throw new PluginNotFoundException("Itemizer");
            }
        } else if (value.getNode("name").getString() != null){
           Optional<ItemType> itemTypeOptional = Sponge.getRegistry().getType(ItemType.class,value.getNode("name").getString());
           if (itemTypeOptional.isPresent()) {
              return ItemStack.builder().itemType(itemTypeOptional.get()).build();
           } else {
               throw new ObjectMappingException("Wrong item name : " + value.getNode("name").getString()
                       + "check the right minecraft name of the item");
           }
        } else {
            return null;
        }


    }

    @Override
    public void serialize(TypeToken<?> type, LootTableBean obj, ConfigurationNode value) throws ObjectMappingException {

    }
}
