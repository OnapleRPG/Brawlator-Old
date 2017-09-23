package com.ylinor.brawlator;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class MonsterSerializer implements TypeSerializer<MonsterBean> {

        @Override
        public MonsterBean deserialize(TypeToken<?> type, ConfigurationNode value)
                throws ObjectMappingException {

             String name = value.getNode("name").getString();
             String entityType = value.getNode("type").getString();
             Integer hp = value.getNode("hp").getInt();
           MonsterBuilder monsterBuilder =  new MonsterBuilder();
           if(name != null){
               monsterBuilder.name(name);
           }
           if(type != null){
               monsterBuilder.type(entityType);
           }
           if(hp != null){
                monsterBuilder.hp(hp);
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
