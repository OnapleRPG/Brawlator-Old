package com.onaple.brawlator.data.handler;


import com.google.common.reflect.TypeToken;
import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.*;
import com.onaple.brawlator.serializer.*;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Singleton
public class ConfigurationHandler {

    public ConfigurationHandler() {}


    private static final List<LootTableBean> lootTableList = new ArrayList<>();
    private static final List<MonsterBean> monsterList = new ArrayList<>();
    private static final List<SpawnerBean> spawnerList = new ArrayList<>();

    public List<LootTableBean> getLootTableList() {
        return lootTableList;
    }

    public List<MonsterBean> getMonsterList() {
        return monsterList;
    }

    public List<SpawnerBean> getSpawnerList() {
        return spawnerList;
    }

    /**
     * Load configuration from files
     * @param configName name of the configuration file
     * @return A commented configuration node
     * @throws Exception
     */
    public CommentedConfigurationNode loadConfiguration(String configName) throws Exception {
        ConfigurationLoader<CommentedConfigurationNode> ConfigLoader = HoconConfigurationLoader.builder().setPath(Paths.get(configName)).build();
        CommentedConfigurationNode configNode = null;
        try {
            configNode = ConfigLoader.load();
            Brawlator.getLogger().info(configName + " was loaded successfully");
        } catch (IOException e) {
            throw new Exception("Error while loading configuration " + configName + " : " + e.getMessage());
        }
        return configNode;
    }


    /**
     * Load the spawner configuration file into a list of Spawner
     * @param configNode the root node of the config file
     * @return  the size of the loaded list
     * @throws ObjectMappingException
     */
    public int setSpawnerList(CommentedConfigurationNode configNode) throws ObjectMappingException {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(SpawnerBean.class), new SpawnerSerializer());

        spawnerList.clear();
        spawnerList.addAll(configNode.getNode("spawner").getList(TypeToken.of(SpawnerBean.class)));

        return spawnerList.size();


    }

    /**
     * Load the lootTable configuration file into a list of LootTableBean
     * @param configNode the root node of the config file
     * @return  the size of the loaded list
     * @throws ObjectMappingException
     */
    public int setLootTableList(CommentedConfigurationNode configNode) throws ObjectMappingException {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(LootTableBean.class), new LootTableSerializer());

        lootTableList.clear();
        lootTableList.addAll(configNode.getNode("loots").getList(TypeToken.of(LootTableBean.class)));
        return lootTableList.size();
    }

    /**
     * Load the monster configuration file into a list of MonsterBean
     * @param configNode the root node of the config file
     * @return  the size of the loaded list
     * @throws ObjectMappingException
     */
    public int setMonsterList(CommentedConfigurationNode configNode) throws ObjectMappingException {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer())
                .registerType(TypeToken.of(EffectBean.class), new EffectSerializer())
                .registerType(TypeToken.of(EquipementBean.class), new EquipementSerialiser());
        monsterList.clear();
        monsterList.addAll(configNode.getNode("monster").getList(TypeToken.of(MonsterBean.class)));
        return monsterList.size();
    }
}
