package com.ylinor.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.LootTableBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class LootTableSerializer implements TypeSerializer<LootTableBean> {
    @Override
    public LootTableBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        LootTableBean lootTableBean = new LootTableBean();
        lootTableBean.setPools(value.getNode("pools").getList(TypeToken.of(Integer.TYPE)));
        lootTableBean.setItems(value.getNode("items").getList(TypeToken.of(Integer.TYPE)));
        lootTableBean.setNames(value.getNode("names").getList(TypeToken.of(String.class)));
    return lootTableBean;
    }

    @Override
    public void serialize(TypeToken<?> type, LootTableBean obj, ConfigurationNode value) throws ObjectMappingException {

    }
}
