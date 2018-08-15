package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.data.beans.EquipementBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class EquipementSerialiser implements TypeSerializer<EquipementBean> {
    @Override
    public EquipementBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        String name = value.getNode("name").getString();
        Integer modifierId = value.getNode("modifierId").getInt();
        return new EquipementBean(name, modifierId);
    }

    @Override
    public void serialize(TypeToken<?> type, EquipementBean obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("name").setValue(obj.getName());
        value.getNode("modifierId").setValue(obj.getModifierId());
    }
}
