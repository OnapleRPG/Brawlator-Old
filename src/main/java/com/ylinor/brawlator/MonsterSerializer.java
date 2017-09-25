package com.ylinor.brawlator;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.List;

public class MonsterSerializer implements TypeSerializer<MonsterBean> {

        @Override
        public MonsterBean deserialize(TypeToken<?> type, ConfigurationNode value)
                throws ObjectMappingException {

             String name = value.getNode("name").getString();
             String entityType = value.getNode("type").getString();
             Integer hp = value.getNode("hp").getInt();
            List<EffectBean> effects = value.getNode("effects").getList(TypeToken.of(EffectBean.class));

            EquipementBean hand = value.getNode("equipement","hand").getValue(TypeToken.of(EquipementBean.class));
            EquipementBean helmet = value.getNode("equipement","helmet").getValue(TypeToken.of(EquipementBean.class));
            EquipementBean chestplate = value.getNode("equipement","chestplate").getValue(TypeToken.of(EquipementBean.class));
            EquipementBean leggings = value.getNode("equipement","leggings").getValue(TypeToken.of(EquipementBean.class));
            EquipementBean boots = value.getNode("equipement","boots").getValue(TypeToken.of(EquipementBean.class));



           MonsterBuilder monsterBuilder =  new MonsterBuilder();
           if (name != null){
               monsterBuilder.name(name);
           }
           if (type != null){
               monsterBuilder.type(entityType);
           }
           if (hp != null){
                monsterBuilder.hp(hp);
            }
            if (effects != null) {
               monsterBuilder.effects(effects);
            }
            if(hand != null){
               monsterBuilder.addEquipement("hand",hand);
            }
            if(helmet != null){
                monsterBuilder.addEquipement("helmet",helmet);
            }
            if(chestplate != null){
                monsterBuilder.addEquipement("chestplate",chestplate);
            }
            if(leggings != null){
                monsterBuilder.addEquipement("leggings",leggings);
            }
            if(boots != null){
                monsterBuilder.addEquipement("boots",boots);
            }

           return monsterBuilder.build();
        }

        @Override
        public void serialize(TypeToken<?> type, MonsterBean obj, ConfigurationNode value)
                throws ObjectMappingException {
            value.getNode("monster","name").setValue(obj.getName());
            value.getNode("monster","hp").setValue(obj.getHp());
        }
    }
