package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.data.beans.EffectBean;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.potion.PotionEffectType;

import java.util.Optional;

public class EffectSerializer implements TypeSerializer<EffectBean> {
    @Override
    public EffectBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
       Optional<PotionEffectType> effectType = Sponge.getRegistry().getType(PotionEffectType.class,value.getNode("type").getString());

       Integer amplifier = value.getNode("amplifier").getInt();

       if(effectType.isPresent()) {

           return new EffectBean(effectType.get(), amplifier);
       }
       else{
           throw new ObjectMappingException();
       }
    }

    @Override
    public void serialize(TypeToken<?> type, EffectBean obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("type").setValue(obj.getType());
        value.getNode("amplifier").setValue(obj.getAmplifier());
    }
}
