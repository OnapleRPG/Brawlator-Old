package com.ylinor.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.LootTableBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.Optional;

public class LootTableSerializer implements TypeSerializer<LootTableBean> {
    @Override
    public LootTableBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        LootTableBean lootTableBean = new LootTableBean();
        lootTableBean.setPool(Optional.of(value.getNode("pool").getValue(TypeToken.of(Integer.TYPE))));
        lootTableBean.setItem(Optional.of(value.getNode("item").getValue(TypeToken.of(Integer.TYPE))));
        lootTableBean.setName(value.getNode("name").getValue(TypeToken.of(String.class)));
    return lootTableBean;
    }

    @Override
    public void serialize(TypeToken<?> type, LootTableBean obj, ConfigurationNode value) throws ObjectMappingException {

    }
}
