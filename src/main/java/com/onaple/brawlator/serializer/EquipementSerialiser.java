package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.data.beans.EquipmentBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class EquipementSerialiser implements TypeSerializer<EquipmentBean> {
    @Override
    public EquipmentBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        String name = value.getNode("name").getString();
        Integer modifierId = value.getNode("modifierId").getInt();
        return new EquipmentBean(name, modifierId);
    }

    @Override
    public void serialize(TypeToken<?> type, EquipmentBean obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("name").setValue(obj.getName());
        value.getNode("modifierId").setValue(obj.getModifierId());
    }
}
