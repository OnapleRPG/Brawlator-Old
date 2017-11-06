package com.ylinor.brawlator.action;

import com.ylinor.brawlator.data.beans.EquipementBean;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class EquipementAction {
    public static Optional<ItemType> getEquipement(EquipementBean equipement){
       return Sponge.getRegistry().getType(ItemType.class, equipement.getName());
    }
}
