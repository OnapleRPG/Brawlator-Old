package com.ylinor.brawlator.data.handler;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.MonsterSerializer;
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

public class ConfigurationHandler {


    private Path configDir;

    public ConfigurationHandler(Path configDir) {
        this.configDir = configDir;
    }

    @Inject
    private Logger logger;

    private CommentedConfigurationNode monster;
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
