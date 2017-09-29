package com.ylinor.brawlator.data.handler;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.EffectSerializer;
import com.ylinor.brawlator.EquipementSerialiser;
import com.ylinor.brawlator.MonsterSerializer;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigurationHandler {



    @Inject
    private Logger logger;


    private static CommentedConfigurationNode monster;

    public static void setConfiguration(CommentedConfigurationNode commentedConfigurationNode){
        monster = commentedConfigurationNode;
    }

    public static Optional<List<MonsterBean>> getMonsterList(){

        try {
            List<MonsterBean> list = monster.getNode("monster").getList(TypeToken.of(MonsterBean.class));
            if (!list.isEmpty()) {
               return Optional.of(list);
            }
            return Optional.empty();
            }
            catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public static void serializeMonsterList(List<MonsterBean> monsterList){
        final TypeToken<List<MonsterBean>> token = new TypeToken<List<MonsterBean>>() {};
        try {

            monster.getNode("monster").setValue(token, monsterList);

        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }






}
