package com.ylinor.brawlator;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.EffectBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class EffectSerializer implements TypeSerializer<EffectBean> {
    @Override
    public EffectBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
       String effectType = value.getNode("type").getString();
       Integer duration = value.getNode("duration").getInt();
       Integer amplifier = value.getNode("amplifier").getInt();

       return new EffectBean(effectType,duration,amplifier);
    }

    @Override
    public void serialize(TypeToken<?> type, EffectBean obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("type").setValue(obj.getType());
        value.getNode("duration").setValue(obj.getDuration());
        value.getNode("amplifier").setValue(obj.getAmplifier());
    }
}
