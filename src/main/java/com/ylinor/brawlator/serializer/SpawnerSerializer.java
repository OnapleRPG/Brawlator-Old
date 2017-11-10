package com.ylinor.brawlator.serializer;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;


import java.util.Optional;

public class SpawnerSerializer implements TypeSerializer<SpawnerBean> {

    @Override
    public SpawnerBean deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
       int id = value.getNode("id").getInt();
        Vector3i position = value.getNode("position").getValue(TypeToken.of(Vector3i.class));
        Optional<MonsterBean> monsterBeanOptional = MonsterDAO.getMonster(value.getNode("monster").getString());
        if(!monsterBeanOptional.isPresent()){
            throw new  ObjectMappingException();
        }
        MonsterBean monsterBean = monsterBeanOptional.get();
        int quantity = value.getNode("quantity").getInt();
        int range = value.getNode("range").getInt();
        int spawnrate = value.getNode("spawnrate").getInt();

        return new SpawnerBean(new Vector3i(-296,71,82), monsterBean,quantity,spawnrate,range);
    }

    @Override
    public void serialize(TypeToken<?> type, SpawnerBean obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("id").setValue(obj.getId());
        value.getNode("position").setValue(obj.getPosition());
        value.getNode("monster").setValue(obj.getMonsterBean().getName());
        value.getNode("quantity").setValue(obj.getQuantity());
        value.getNode("range").setValue(obj.getRange());
        value.getNode("spawnrate").setValue(obj.getSpawnRate());
        }
}
