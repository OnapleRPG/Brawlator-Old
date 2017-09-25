package com.ylinor.brawlator.data.handler;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.EffectSerializer;
import com.ylinor.brawlator.EquipementSerialiser;
import com.ylinor.brawlator.MonsterSerializer;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
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

public class ConfigurationHandler {



    @Inject
    private Logger logger;

    private static List<MonsterBean> monsterList;
    private static CommentedConfigurationNode monster;

    public static List<MonsterBean> getMonsterList(){
        return monsterList;
    }

    public static void setConfiguration(CommentedConfigurationNode commentedConfigurationNode){
        monster = commentedConfigurationNode;
    }

    public static void createMonsterList(){
        monsterList = new ArrayList<>();
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(EffectBean.class), new EffectSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(EquipementBean.class), new EquipementSerialiser());

        try {
            monsterList = monster.getNode("monster").getList(TypeToken.of(MonsterBean.class));

            for (MonsterBean monster:monsterList
                 ) {
                System.out.println(monster.getName());

            }
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

  /*  public CommentedConfigurationNode getMonster() {
        return monster;
    }
    public void createMonster(){
        monster = loadConfiguration("monster.conf");

        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer());
        ConfigurationNode targetNode = monster.getNode("monster");

        try {
           MonsterBean monsterBean = targetNode.getValue(TypeToken.of(MonsterBean.class));
            logger.info(monsterBean.getName());
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

    }*/



}
