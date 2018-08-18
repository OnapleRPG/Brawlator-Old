package com.onaple.brawlator.action;

import com.onaple.brawlator.data.beans.EquipmentBean;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;

import java.util.Optional;


public class EquipementAction {
    /**
     * Get the ItemType from the equipment bean
     *
     * @param equipment Equipment from which
     * @return Optional of item type
     */
    public static Optional<ItemType> getEquipment(EquipmentBean equipment) {
        return Sponge.getRegistry().getType(ItemType.class, equipment.getName());
    }
}
