package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.data.beans.LootTableBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class LootTableSerializer implements TypeSerializer<LootTableBean> {
    @Override
    public LootTableBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
    LootTableBean lootTableBean = new LootTableBean(value.getNode("pool").getInt(),
            value.getNode("item").getInt(),
            value.getNode("itemname").getString(),
            value.getNode("name").getString());

    return lootTableBean;
    }

    @Override
    public void serialize(TypeToken<?> type, LootTableBean obj, ConfigurationNode value) throws ObjectMappingException {

    }
}
