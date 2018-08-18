package com.onaple.brawlator.serializer;

import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.data.beans.EffectBean;
import com.onaple.brawlator.data.beans.EquipmentBean;
import com.onaple.brawlator.data.beans.MonsterBean;
import com.onaple.brawlator.data.beans.MonsterBuilder;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.List;
import java.util.Map;

public class MonsterSerializer implements TypeSerializer<MonsterBean> {

    @Override
    public MonsterBean deserialize(TypeToken<?> type, ConfigurationNode value)
            throws ObjectMappingException {
        String name = value.getNode("name").getString();
        String entityType = value.getNode("type").getString();
        Integer hp = value.getNode("hp").getInt();
        Double speed = value.getNode("speed").getDouble();
        Integer damage = value.getNode("damage").getInt();
        Integer knockbackResistance = value.getNode("knockbackResistance").getInt();

        List<EffectBean> effects = value.getNode("effects").getList(TypeToken.of(EffectBean.class));

        EquipmentBean hand = value.getNode("equipement", "hand").getValue(TypeToken.of(EquipmentBean.class));
        EquipmentBean helmet = value.getNode("equipement", "helmet").getValue(TypeToken.of(EquipmentBean.class));
        EquipmentBean chestplate = value.getNode("equipement", "chestplate").getValue(TypeToken.of(EquipmentBean.class));
        EquipmentBean leggings = value.getNode("equipement", "leggings").getValue(TypeToken.of(EquipmentBean.class));
        EquipmentBean boots = value.getNode("equipement", "boots").getValue(TypeToken.of(EquipmentBean.class));

        MonsterBuilder monsterBuilder = new MonsterBuilder();

        monsterBuilder.hp(hp);
        monsterBuilder.attackDamage(damage);
        monsterBuilder.knockbackResistance(knockbackResistance);
        monsterBuilder.speed(speed);
        if (name != null) {
            monsterBuilder.name(name);
        }
        if (type != null) {
            monsterBuilder.type(entityType);
        }
        if (effects != null) {
            monsterBuilder.effects(effects);
        }
        if (hand != null) {
            monsterBuilder.addEquipement("hand", hand);
        }
        if (helmet != null) {
            monsterBuilder.addEquipement("helmet", helmet);
        }
        if (chestplate != null) {
            monsterBuilder.addEquipement("chestplate", chestplate);
        }
        if (leggings != null) {
            monsterBuilder.addEquipement("leggings", leggings);
        }
        if (boots != null) {
            monsterBuilder.addEquipement("boots", boots);
        }

        return monsterBuilder.build();
    }

    @Override
    public void serialize(TypeToken<?> type, MonsterBean obj, ConfigurationNode value)
            throws ObjectMappingException {
        value.getNode("name").setValue(obj.getName());
        value.getNode("hp").setValue(Math.floor(obj.getHp()));
        value.getNode("type").setValue(obj.getType());
        value.getNode("speed").setValue(obj.getSpeed());
        value.getNode("knockbackResistance").setValue(obj.getKnockbackResistance());
        value.getNode("damage").setValue(obj.getAttackDamage());

        final TypeToken<List<EffectBean>> token = new TypeToken<List<EffectBean>>() {};
        value.getNode("effects").setValue(token, obj.getEffectLists());

        for (Map.Entry<String, EquipmentBean> entry : obj.getEquipement().entrySet()) {
            value.getNode("equipement", entry.getKey()).setValue(TypeToken.of(EquipmentBean.class), entry.getValue());
        }
    }
}
