package com.onaple.brawlator.action;

import com.onaple.brawlator.data.beans.EquipementBean;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;

import java.util.Optional;

public class EquipementAction {
    /**
     * get the ItemType from the equipement bean
     * @param equipement
     * @return
     */
    public static Optional<ItemType> getEquipement(EquipementBean equipement){
       return Sponge.getRegistry().getType(ItemType.class, equipement.getName());
    }
}
